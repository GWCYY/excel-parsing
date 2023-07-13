package com.rookiesquad.excelparsing.listener.content;

import com.rookiesquad.excelparsing.dto.BillData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author lianghonglei
 */
public class BillExcelListener extends CustomContentExcelListener<BillData> {

    private static final Logger logger = LoggerFactory.getLogger(BillExcelListener.class);

    @Override
    protected void dealContent(Map<Integer, String> data) {
        BillData billData = new BillData();
        logger.info("start fillSourceDataList...");
        fillSourceDataList(billData, data);
    }

}

