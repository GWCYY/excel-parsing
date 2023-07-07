package com.rookiesquad.excelparsing.runable;

import com.alibaba.excel.EasyExcelFactory;
import com.rookiesquad.excelparsing.component.ApplicationUtils;
import com.rookiesquad.excelparsing.component.MeterHeaderConfiguration;
import com.rookiesquad.excelparsing.constant.ReconciliationType;
import com.rookiesquad.excelparsing.dto.BaseData;
import com.rookiesquad.excelparsing.entity.ReconciliationData;
import com.rookiesquad.excelparsing.exception.ExcelErrorCode;
import com.rookiesquad.excelparsing.exception.ExcelException;
import com.rookiesquad.excelparsing.listener.content.BillExcelListener;
import com.rookiesquad.excelparsing.listener.content.CustomContentExcelListener;
import com.rookiesquad.excelparsing.listener.content.PaidInExcelListener;
import com.rookiesquad.excelparsing.repository.ReconciliationDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ExcelParsingRunnable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ExcelParsingRunnable.class);

    private CountDownLatch countDownLatch;
    private String filePath;
    private ReconciliationType reconciliationType;

    @Override
    public void run() {
        logger.info("Start parsing excel : [{}]", filePath);
        CustomContentExcelListener<? extends BaseData> customContentExcelListener;
        switch (reconciliationType) {
            case BILL -> customContentExcelListener = new BillExcelListener();
            case PAID_IN -> customContentExcelListener = new PaidInExcelListener();
            default -> throw new ExcelException(ExcelErrorCode.ILLEGAL_RECONCILIATION_TYPE);
        }
        customContentExcelListener.setMeterHeaderConfiguration(ApplicationUtils.getBean(MeterHeaderConfiguration.class));
        // TODO 需要根据head监听获取到sheet
        EasyExcelFactory.read(filePath, customContentExcelListener).sheet("合肥").doRead();
        logger.info("Parsing excel : [{}] finish", filePath);
        List<ReconciliationData> reconciliationDataList = new ArrayList<>();
        customContentExcelListener.getSourceDataList().forEach(sourceData -> {
            if (!StringUtils.hasText(sourceData.getCardNo())){
                return;
            }
            logger.info(sourceData.toString());
            ReconciliationData reconciliationData = new ReconciliationData();
            reconciliationData.setCardNo(sourceData.getCardNo());
            reconciliationData.setName(sourceData.getName());
            reconciliationData.setTotalSocialInsuranceBenefit(sourceData.getTotalSocialInsuranceBenefit());
            reconciliationData.setTotalProvidentFund(sourceData.getTotalProvidentFund());
            reconciliationData.setType(reconciliationType.getCode());
            reconciliationDataList.add(reconciliationData);
        });
        ReconciliationDataRepository reconciliationDataRepository = ApplicationUtils.getBean(ReconciliationDataRepository.class);
        reconciliationDataRepository.saveAll(reconciliationDataList);
        countDownLatch.countDown();
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setReconciliationType(ReconciliationType reconciliationType) {
        this.reconciliationType = reconciliationType;
    }

}
