package com.rookiesquad.excelparsing.listener.content;

import com.alibaba.excel.context.AnalysisContext;
import com.rookiesquad.excelparsing.dto.PayData;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author lianghonglei
 */
public class PayExcelListener extends CustomContentExcelListener<PayData> {

    /**
     * 进行读的操作具体执行方法，一行一行的读取数据
     * 从第二行开始读取，不读取表头
     *
     * @param data
     * @param context
     */
    @Override
    public void invoke(PayData data, AnalysisContext context) {
        if (Objects.isNull(data) || !StringUtils.hasText(data.getCardNo())) {
            return;
        }
        super.invoke(data, context);
    }

}

