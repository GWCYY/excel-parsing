package com.rookiesquad.excelparsing.listener.content;

import com.rookiesquad.excelparsing.dto.PaidInData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lianghonglei
 */
@Service
public class PaidInExcelListener extends CustomContentExcelListener<PaidInData> {

    private static final Logger logger = LoggerFactory.getLogger(PaidInExcelListener.class);

    @Override
    protected void dealContent(Map<Integer, String> data) {
        PaidInData paidInData = new PaidInData();
        fillSourceDataList(paidInData, data);
    }
}

