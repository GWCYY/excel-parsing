package com.rookiesquad.excelparsing.listener.content;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.rookiesquad.excelparsing.dto.BaseData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CustomContentExcelListener<T extends BaseData> extends AnalysisEventListener<T> {


    protected final List<T> sourceDataList = new ArrayList<>();


    /**
     * 进行读的操作具体执行方法，一行一行的读取数据
     * 从第二行开始读取，不读取表头
     *
     * @param data
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        sourceDataList.add(data);
        System.out.println("读取到一条数据：" + data);
    }
    /**
     * 读取表头信息
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public List<T> getSourceDataList() {
        return sourceDataList;
    }
}
