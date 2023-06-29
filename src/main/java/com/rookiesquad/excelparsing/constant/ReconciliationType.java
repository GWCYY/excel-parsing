package com.rookiesquad.excelparsing.constant;

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
}
