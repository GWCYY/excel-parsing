package com.rookiesquad.excelparsing.service;

import com.alibaba.excel.EasyExcel;
import com.rookiesquad.excelparsing.component.MeterHeaderConfiguration;
import com.rookiesquad.excelparsing.constant.ReconciliationType;
import com.rookiesquad.excelparsing.controller.ExcelController;
import com.rookiesquad.excelparsing.listener.content.BillExcelListener;
import com.rookiesquad.excelparsing.listener.content.CustomContentExcelListener;
import com.rookiesquad.excelparsing.listener.content.PaidInExcelListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class ExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

    private final MeterHeaderConfiguration meterHeaderConfiguration;

    public ExcelService(MeterHeaderConfiguration meterHeaderConfiguration) {
        this.meterHeaderConfiguration = meterHeaderConfiguration;
    }

    public void testRead(String filePath) {
        filePath = URLDecoder.decode(filePath, StandardCharsets.UTF_8);
        CustomContentExcelListener<?> customContentExcelListener = null;
        ReconciliationType reconciliationType;
        if (filePath.contains("账单")) {
            reconciliationType = ReconciliationType.BILL;
        } else {
            reconciliationType = ReconciliationType.PAID_IN;
        }
        switch (reconciliationType) {
            case BILL -> {
                customContentExcelListener = new BillExcelListener();
            }
            case PAID_IN -> {
                customContentExcelListener = new PaidInExcelListener();
            }
            default -> logger.warn("Not Supported");
        }
        customContentExcelListener.setMeterHeaderConfiguration(meterHeaderConfiguration);
        EasyExcel.read(filePath, customContentExcelListener).sheet("合肥").doRead();
        customContentExcelListener.getSourceDataList().forEach(System.out::println);
    }
}
