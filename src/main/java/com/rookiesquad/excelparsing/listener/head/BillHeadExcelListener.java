package com.rookiesquad.excelparsing.listener.head;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author lianghonglei
 */
public class BillHeadExcelListener extends CustomHeadExcelListener {

    /**
     * 表头行数
     */
    private int headerRow = -1;


    /**
     * 进行读的操作具体执行方法，一行一行的读取数据
     * 从第二行开始读取，不读取表头
     *
     * @param data
     * @param context
     */
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        if (headerRow == -1) {
            boolean hasIdCardNo = data.containsValue("身份证号码") || data.containsValue("公积金合计");
            if (hasIdCardNo) {
                headerRow = context.readRowHolder().getRowIndex();
            }
        }
    }

}

