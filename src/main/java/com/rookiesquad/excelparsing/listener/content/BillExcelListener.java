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
        if (currentHeader != null) {
            BillData billData = new BillData();
            columnIndexMap.keySet().forEach(columnIndex -> {
                String columnName = columnIndexMap.get(columnIndex);
                String columnValue = data.get(columnIndex);
                switch (columnName) {
                    case "cardNum" -> billData.setCardNo(columnValue);
                    case "name" -> billData.setName(columnValue);
                    case "totalSocialInsuranceBenefit" -> billData.setTotalSocialInsuranceBenefit(columnValue);
                    case "totalProvidentFund" -> billData.setTotalProvidentFund(columnValue);
                    default -> logger.warn("Unnecessary data is not saved");
                }
            });
            sourceDataList.add(billData);
        }
    }
}

