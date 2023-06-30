package com.rookiesquad.excelparsing.constant;

import com.rookiesquad.excelparsing.exception.ExcelErrorCode;
import com.rookiesquad.excelparsing.exception.ExcelException;

public enum ReconciliationType {
    BILL(0, "账单"),
    PAID_IN(1, "实缴"),
    ;

    private final Integer code;
    private final String description;


    ReconciliationType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ReconciliationType getByCode(Integer code){
        for (ReconciliationType reconciliation : ReconciliationType.values()) {
            if (reconciliation.getCode().equals(code)){
                return reconciliation;
            }
        }
        throw new ExcelException(ExcelErrorCode.ILLEGAL_RECONCILIATION_TYPE);
    }
}
