package com.rookiesquad.excelparsing.listener.content;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;
import com.rookiesquad.excelparsing.component.MeterHeaderConfiguration;
import com.rookiesquad.excelparsing.dto.BaseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public abstract class CustomContentExcelListener<T extends BaseData> extends AnalysisEventListener<Map<Integer, String>> {

    private static final Logger logger = LoggerFactory.getLogger(CustomContentExcelListener.class);

    protected final List<T> sourceDataList = new ArrayList<>();

    protected String currentHeader;
    protected Map<Integer, String> columnIndexMap = new HashMap<>();

    private MeterHeaderConfiguration meterHeaderConfiguration;

    /**
     * 进行读的操作具体执行方法，一行一行的读取数据
     * 从第二行开始读取，不读取表头
     *
     * @param data    读取到的数据
     * @param context analysis context
     */
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        List<String> cardNoList = meterHeaderConfiguration.getCardNoNameList();
        boolean hasIdCardNo = false;
        for (String cardNoName : cardNoList) {
            hasIdCardNo = data.containsValue(cardNoName);
            if (hasIdCardNo) {
                break;
            }
        }
        if (hasIdCardNo) {
            dealHead(data);
        } else {
            if (currentHeader != null) {
                dealContent(data);
            }
        }
    }

    protected abstract void dealContent(Map<Integer, String> data);

    protected void fillSourceDataList(T result, Map<Integer, String> data) {
        columnIndexMap.keySet().forEach(columnIndex -> {
            String columnName = columnIndexMap.get(columnIndex);
            String columnValue = data.get(columnIndex);
            switch (columnName) {
                case "cardNum" -> result.setCardNo(columnValue);
                case "name" -> result.setName(columnValue);
                case "totalSocialInsuranceBenefit" -> result.setTotalSocialInsuranceBenefit(Optional.ofNullable(columnValue).orElse("0"));
                case "totalProvidentFund" -> result.setTotalProvidentFund(Optional.ofNullable(columnValue).orElse("0"));
                default -> logger.warn("Unnecessary data is not saved");
            }
        });
        sourceDataList.add(result);
    }

    private void dealHead(Map<Integer, String> data) {
        currentHeader = JSON.toJSONString(data);
        // 当前行是表头行
        data.forEach((columnIndex, columnValue) -> {
            if (null == columnValue) {
                return;
            }
            List<String> cardNoNameList = meterHeaderConfiguration.getCardNoNameList();
            List<String> nameNameList = meterHeaderConfiguration.getNameNameList();
            List<String> totalSocialInsuranceBenefitNameList = meterHeaderConfiguration.getTotalSocialInsuranceBenefitNameList();
            List<String> totalProvidentFundNameList = meterHeaderConfiguration.getTotalProvidentFundNameList();
            if (cardNoNameList.contains(columnValue)) {
                columnIndexMap.put(columnIndex, "cardNum");
            }
            if (nameNameList.contains(columnValue)) {
                columnIndexMap.put(columnIndex, "name");
            }

            if (totalSocialInsuranceBenefitNameList.contains(columnValue)) {
                columnIndexMap.put(columnIndex, "totalSocialInsuranceBenefit");
            }
            if (totalProvidentFundNameList.contains(columnValue)) {
                columnIndexMap.put(columnIndex, "totalProvidentFund");
            }
        });
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        logger.info("doAfterAllAnalysed");
    }

    public List<T> getSourceDataList() {
        return sourceDataList;
    }

    public void setMeterHeaderConfiguration(MeterHeaderConfiguration meterHeaderConfiguration) {
        this.meterHeaderConfiguration = meterHeaderConfiguration;
    }
}
