package com.rookiesquad.excelparsing.listener.head;

import com.alibaba.excel.context.AnalysisContext;

import java.util.Map;

/**
 * @author lianghonglei
 */
public class PayHeadExcelListener extends CustomHeadExcelListener {

    /**
     * 进行读的操作具体执行方法，一行一行的读取数据
     * 从第二行开始读取，不读取表头
     *
     * @param data 读取到的数据
     * @param context analysis context
     */
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        if (headerRow == -1) {
            boolean hasIdCardNo = data.containsValue("身份证");
            if (hasIdCardNo) {
                headerRow = context.readRowHolder().getRowIndex();
            }
        }
    }

}

