package com.rookiesquad.excelparsing.exception;

public enum ResultCode {

    INTERNAL_SERVER_ERROR(500, "{INTERNAL_SERVER_ERROR}");

    private final long code;
    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
