package com.rookiesquad.excelparsing.service;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.rookiesquad.excelparsing.constant.ReconciliationType;
import com.rookiesquad.excelparsing.entity.AnalysisConfiguration;
import com.rookiesquad.excelparsing.entity.ReconciliationResult;
import com.rookiesquad.excelparsing.exception.ExcelErrorCode;
import com.rookiesquad.excelparsing.exception.ExcelException;
import com.rookiesquad.excelparsing.repository.AnalysisConfigurationRepository;
import com.rookiesquad.excelparsing.repository.ReconciliationResultRepository;
import com.rookiesquad.excelparsing.runable.ExcelParsingRunnable;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExcelService implements BaseService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);

    private static final String SYSTEM_TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");
    private static final String DEFAULT_EXCEL_SUFFIX = ".xlsx";

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final AnalysisConfigurationRepository analysisConfigurationRepository;
    private final ReconciliationResultRepository reconciliationResultRepository;

    /**
     * 单sheet页最大数据量
     */
    @Value("${excel.export.single-sheet-data-number:3000}")
    private int singleSheetDataNumber;
    /**
     * 单文件最大sheet数量
     */
    @Value("${excel.export.sheet-number:10}")
    private int maxSheetNumber;

    public ExcelService(ThreadPoolTaskExecutor threadPoolTaskExecutor,
                        AnalysisConfigurationRepository analysisConfigurationRepository,
                        ReconciliationResultRepository reconciliationResultRepository) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.analysisConfigurationRepository = analysisConfigurationRepository;
        this.reconciliationResultRepository = reconciliationResultRepository;
    }


    public void testRead(Long batchNumber) {
        AnalysisConfiguration analysisConfiguration = analysisConfigurationRepository.findByBatchNumber(batchNumber);
        String filePath = analysisConfiguration.getFilePath();
        filePath = filePath + File.separator + "1有限公司202305费用明细.xls";
        ReconciliationType reconciliationType = ReconciliationType.getByCode(analysisConfiguration.getReconciliationType());
        ExcelParsingRunnable excelParsingRunnable = new ExcelParsingRunnable();
        excelParsingRunnable.setCountDownLatch(new CountDownLatch(1));
        excelParsingRunnable.setFilePath(filePath);
        excelParsingRunnable.setReconciliationType(reconciliationType);
        Future<Integer> parsingResult = threadPoolTaskExecutor.submit(excelParsingRunnable);
        updateParsingResult(analysisConfiguration, parsingResult);
    }

    @Transactional
    public void parsingExcel(Long batchNumber) {
        AnalysisConfiguration analysisConfiguration = analysisConfigurationRepository.findByBatchNumber(batchNumber);
        String filePath = analysisConfiguration.getFilePath();
        File folder = new File(filePath);
        if (!folder.exists()) {
            throw new ExcelException(ExcelErrorCode.INVALID_FILE_PATH);
        }
        List<String> filePathList = new ArrayList<>();
        if (folder.isDirectory()) {
            filePathList = listFolder(folder);
        } else {
            filePathList.add(filePath);
        }
        int reconciliationType = analysisConfiguration.getReconciliationType();
        startParsing(filePathList, ReconciliationType.getByCode(reconciliationType), analysisConfiguration);
    }

    private void startParsing(List<String> fileList, ReconciliationType reconciliationType, AnalysisConfiguration analysisConfiguration) {
        int maxPoolSize = threadPoolTaskExecutor.getMaxPoolSize();
        CountDownLatch countDownLatch = new CountDownLatch(maxPoolSize);
        AtomicInteger batchCount = new AtomicInteger(maxPoolSize);
        for (String filePath : fileList) {
            if (batchCount.get() <= 0) {
                while (countDownLatch.getCount() >= 0) {
                    if (countDownLatch.getCount() <= 0) {
                        countDownLatch = new CountDownLatch(maxPoolSize);
                        batchCount = new AtomicInteger(maxPoolSize);
                    }
                }
            }
            ExcelParsingRunnable excelParsingRunnable = new ExcelParsingRunnable();
            excelParsingRunnable.setCountDownLatch(countDownLatch);
            excelParsingRunnable.setFilePath(filePath);
            excelParsingRunnable.setReconciliationType(reconciliationType);
            Future<Integer> parsingResult = threadPoolTaskExecutor.submit(excelParsingRunnable);
            batchCount.getAndDecrement();
            updateParsingResult(analysisConfiguration, parsingResult);
        }
    }

    private void updateParsingResult(AnalysisConfiguration analysisConfiguration, Future<Integer> parsingResult) {
        while (true) {
            if (parsingResult.isDone() || parsingResult.isCancelled()) {
                try {
                    analysisConfiguration.setParsingResult(parsingResult.get());
                } catch (InterruptedException | ExecutionException exception) {
                    Thread.currentThread().interrupt();
                    throw new ExcelException(ExcelErrorCode.PARSING_FILE_FAILED);
                }
                break;
            }
        }
    }

    private List<String> listFolder(File folder) {
        List<String> filePathList = new ArrayList<>();
        File[] files = folder.listFiles();
        if (null == files) {
            return Collections.emptyList();
        }
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile() && (fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))) {
                filePathList.add(folder.getAbsolutePath() + File.separator + fileName);
            }
            if (file.isDirectory()) {
                filePathList.addAll(listFolder(file));
            }
        }
        return filePathList;
    }

    public Page<ReconciliationResult> pageParsingExcelResult(Pageable pageable) {
        return reconciliationResultRepository.findReconciliationResult(pageable);
    }

    public void downloadParsingExcelResult(HttpServletResponse response) throws IOException {
        int reconciliationDataCount = reconciliationResultRepository.findAllReconciliationDataCount();
        int sheetNumber = reconciliationDataCount / singleSheetDataNumber + 1;
        String fileName = null;
        PageRequest pageRequest;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExcelWriter excelWriter = EasyExcelFactory.write(byteArrayOutputStream).excludeColumnFieldNames(ReconciliationResult.EXCLUDE_FIELD).build();
        for (int i = 0; i < sheetNumber; i++) {
            pageRequest = PageRequest.of(i, singleSheetDataNumber);
            Page<ReconciliationResult> reconciliationResultPage = reconciliationResultRepository.findReconciliationResult(pageRequest);
            List<ReconciliationResult> currentBatchData = reconciliationResultPage.getContent();
            boolean firstSheet = i % maxSheetNumber == 0;
            if (firstSheet) {
                if (byteArrayOutputStream.size() > 0) {
                    writeParsingExcelResult(fileName, byteArrayOutputStream);
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    excelWriter = EasyExcelFactory.write(byteArrayOutputStream, ReconciliationResult.class).build();
                }
                fileName = SYSTEM_TEMP_DIRECTORY + File.separator + System.currentTimeMillis() + DEFAULT_EXCEL_SUFFIX;
            }
            WriteSheet writeSheet = EasyExcelFactory
                    .writerSheet("reconciliationResult" + i)
                    .head(ReconciliationResult.class)
                    .excludeColumnFieldNames(ReconciliationResult.EXCLUDE_FIELD)
                    .build();
            excelWriter.write(currentBatchData, writeSheet);
            if(maxSheetNumber == (i + 1) || sheetNumber == (i + 1)){
                excelWriter.finish();
            }
        }
        if (byteArrayOutputStream.size() > 0) {
            excelWriter.finish();
            writeParsingExcelResult(fileName, byteArrayOutputStream);
        }
        // TODO 还需要对各文件进行打包下载
    }

    private static void writeParsingExcelResult(String fileName, ByteArrayOutputStream byteArrayOutputStream) {
        try {
            Files.write(Paths.get(fileName), byteArrayOutputStream.toByteArray(), StandardOpenOption.CREATE);
        } catch (IOException exception) {
            throw new ExcelException(ExcelErrorCode.WRITE_FILE_FAILED);
        }
    }

}
