package com.rookiesquad.excelparsing.listener.content;

import com.alibaba.excel.context.AnalysisContext;
import com.rookiesquad.excelparsing.dto.BillData;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author lianghonglei
 */
public class BillExcelListener extends CustomContentExcelListener<BillData> {

    @Override
    public void invoke(BillData data, AnalysisContext context) {
        if (Objects.isNull(data) || !StringUtils.hasText(data.getCardNo())) {
            return;
        }
        super.invoke(data, context);
    }
}

