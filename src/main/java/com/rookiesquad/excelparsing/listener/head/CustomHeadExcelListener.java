package com.rookiesquad.excelparsing.listener.head;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.rookiesquad.excelparsing.component.MeterHeaderConfiguration;
import com.rookiesquad.excelparsing.constant.CommonConstants;

import java.util.List;
import java.util.Map;

public abstract class CustomHeadExcelListener extends AnalysisEventListener<Map<Integer, String>> {

    protected int headerRow = CommonConstants.DEFAULT_HEAD_ROW_NUM;
    protected MeterHeaderConfiguration meterHeaderConfiguration;

    /**
     * 进行读的操作具体执行方法，一行一行的读取数据
     * 从第二行开始读取，不读取表头
     *
     * @param data    读取到的数据
     * @param context analysis context
     */
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        if (headerRow == CommonConstants.DEFAULT_HEAD_ROW_NUM) {
            List<String> cardNoName = meterHeaderConfiguration.getCardNoNameList();
            for (String rowValue : data.values()) {
                if (cardNoName.contains(rowValue)) {
                    headerRow = context.readRowHolder().getRowIndex();
                }
            }
        }
    }

    /**
     * 读取完数据的操作
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    public int getHeaderRow() {
        return headerRow;
    }

    public void setHeaderRow(int headerRow) {
        this.headerRow = headerRow;
    }

    public void setMeterHeaderConfiguration(MeterHeaderConfiguration meterHeaderConfiguration) {
        this.meterHeaderConfiguration = meterHeaderConfiguration;
    }
}
