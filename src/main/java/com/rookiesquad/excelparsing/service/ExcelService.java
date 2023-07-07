package com.rookiesquad.excelparsing.service;

import com.rookiesquad.excelparsing.constant.ReconciliationType;
import com.rookiesquad.excelparsing.entity.AnalysisConfiguration;
import com.rookiesquad.excelparsing.exception.ExcelErrorCode;
import com.rookiesquad.excelparsing.exception.ExcelException;
import com.rookiesquad.excelparsing.repository.AnalysisConfigurationRepository;
import com.rookiesquad.excelparsing.runable.ExcelParsingRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExcelService implements BaseService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final AnalysisConfigurationRepository analysisConfigurationRepository;

    public ExcelService(ThreadPoolTaskExecutor threadPoolTaskExecutor, AnalysisConfigurationRepository analysisConfigurationRepository) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.analysisConfigurationRepository = analysisConfigurationRepository;
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
        threadPoolTaskExecutor.execute(excelParsingRunnable);
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
        startParsing(filePathList, ReconciliationType.getByCode(reconciliationType));

    }

    private void startParsing(List<String> fileList, ReconciliationType reconciliationType) {
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
            threadPoolTaskExecutor.execute(excelParsingRunnable);
            batchCount.getAndDecrement();
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
                filePathList.add(folder.getAbsolutePath() + File.separator + file);
            }
            if (file.isDirectory()) {
                filePathList.addAll(listFolder(file));
            }
        }
        return filePathList;
    }
}
