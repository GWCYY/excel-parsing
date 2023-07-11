package com.rookiesquad.excelparsing.runable;

import com.rookiesquad.excelparsing.component.ApplicationUtils;
import com.rookiesquad.excelparsing.constant.ReconciliationType;
import com.rookiesquad.excelparsing.dto.BaseData;
import com.rookiesquad.excelparsing.exception.ExcelErrorCode;
import com.rookiesquad.excelparsing.exception.ExcelException;
import com.rookiesquad.excelparsing.service.ExcelParsingService;
import com.rookiesquad.excelparsing.service.impl.BillParsingService;
import com.rookiesquad.excelparsing.service.impl.PaidInParsingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class ExcelParsingRunnable implements Callable<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(ExcelParsingRunnable.class);

    private CountDownLatch countDownLatch;
    private String filePath;
    private ReconciliationType reconciliationType;

    @Override
    public Integer call() {
        logger.info("Start parsing excel : [{}]", filePath);
        long startTime = System.currentTimeMillis();
        ExcelParsingService<? extends BaseData> excelParsingService;
        switch (reconciliationType) {
            case BILL -> excelParsingService = ApplicationUtils.getBean(BillParsingService.class);
            case PAID_IN -> excelParsingService = ApplicationUtils.getBean(PaidInParsingService.class);
            default -> throw new ExcelException(ExcelErrorCode.ILLEGAL_RECONCILIATION_TYPE);
        }
        Integer parsingResult = excelParsingService.parsing(filePath);
        countDownLatch.countDown();
        long endTime = System.currentTimeMillis();
        logger.info("Parsing file [{}] takes time: [{}]ms", filePath, endTime - startTime);
       return parsingResult;
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
