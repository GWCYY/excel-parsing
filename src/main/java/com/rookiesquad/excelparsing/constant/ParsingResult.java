package com.rookiesquad.excelparsing.constant;

public enum ParsingResult {

    FAILED(0, "FAILED"),
    SUCCESS(1, "SUCCESS");

    private final Integer code;
    private final String description;

    ParsingResult(Integer code, String description) {
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
