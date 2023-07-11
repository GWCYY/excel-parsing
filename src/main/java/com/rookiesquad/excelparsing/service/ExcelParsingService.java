package com.rookiesquad.excelparsing.service;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.rookiesquad.excelparsing.component.MeterHeaderConfiguration;
import com.rookiesquad.excelparsing.constant.CommonConstants;
import com.rookiesquad.excelparsing.constant.ParsingResult;
import com.rookiesquad.excelparsing.constant.ReconciliationType;
import com.rookiesquad.excelparsing.dto.BaseData;
import com.rookiesquad.excelparsing.entity.ReconciliationData;
import com.rookiesquad.excelparsing.listener.content.CustomContentExcelListener;
import com.rookiesquad.excelparsing.listener.head.CustomHeadExcelListener;
import com.rookiesquad.excelparsing.repository.ReconciliationDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public abstract class ExcelParsingService<T extends BaseData> implements BaseService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelParsingService.class);

    protected CustomHeadExcelListener customHeadExcelListener;
    protected CustomContentExcelListener<T> customContentExcelListener;
    protected ReconciliationDataRepository<? extends ReconciliationData> reconciliationDataRepository;
    protected MeterHeaderConfiguration meterHeaderConfiguration;

    @Autowired
    public void setMeterHeaderConfiguration(MeterHeaderConfiguration meterHeaderConfiguration) {
        this.meterHeaderConfiguration = meterHeaderConfiguration;
    }

    public abstract Integer parsing(String filePath);

    protected Integer parsingExcel(String filePath) {
        customContentExcelListener.setMeterHeaderConfiguration(meterHeaderConfiguration);
        customHeadExcelListener.setMeterHeaderConfiguration(meterHeaderConfiguration);
        ExcelReader excelReader = EasyExcelFactory.read(filePath).build();
        ExcelReadExecutor excelReadExecutor = excelReader.excelExecutor();
        Map<Integer, Integer> headRowNumBySheetName = new HashMap<>();
        for (ReadSheet sheet : excelReadExecutor.sheetList()) {
            ExcelReaderBuilder readerBuilder = EasyExcelFactory.read(filePath, customHeadExcelListener);
            readerBuilder.sheet(sheet.getSheetNo()).headRowNumber(0).doRead();
            int headerRowNum = customHeadExcelListener.getHeaderRow();
            if (headerRowNum != CommonConstants.DEFAULT_HEAD_ROW_NUM) {
                headRowNumBySheetName.put(sheet.getSheetNo(), customHeadExcelListener.getHeaderRow());
                customHeadExcelListener.setHeaderRow(CommonConstants.DEFAULT_HEAD_ROW_NUM);
            }
        }
        headRowNumBySheetName.forEach((sheetNo, headRowNum) -> EasyExcelFactory.read(filePath, customContentExcelListener)
                .sheet(sheetNo).headRowNumber(headRowNum).doRead());
        logger.info("Parsing excel : [{}] finish", filePath);
        addParsingData();
        return ParsingResult.SUCCESS.getCode();
    }

    protected abstract void addParsingData();

    protected <V extends ReconciliationData> void buildReconciliationData(V reconciliationData, T sourceData, List<V> reconciliationDataList){
        reconciliationData.setCardNo(sourceData.getCardNo());
        reconciliationData.setName(sourceData.getName());
        String totalSocialInsuranceBenefit = sourceData.getTotalSocialInsuranceBenefit();
        totalSocialInsuranceBenefit = StringUtils.hasText(totalSocialInsuranceBenefit) ? totalSocialInsuranceBenefit : "0";
        reconciliationData.setTotalSocialInsuranceBenefit(Float.parseFloat(totalSocialInsuranceBenefit));
        String totalProvidentFund = sourceData.getTotalProvidentFund();
        totalProvidentFund = StringUtils.hasText(totalProvidentFund) ? totalProvidentFund : "0";
        reconciliationData.setTotalProvidentFund(Float.parseFloat(totalProvidentFund));
        reconciliationData.setType(ReconciliationType.PAID_IN.getCode());
        reconciliationDataList.add(reconciliationData);
    }
}
