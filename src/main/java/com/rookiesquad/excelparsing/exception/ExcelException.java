package com.rookiesquad.excelparsing.exception;

import java.io.Serial;

public class ExcelException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3561028341638460706L;

    private final long code;

    public ExcelException(ExcelErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public ExcelException(long code, String message) {
        super(message);
        this.code = code;
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultCode.INTERNAL_SERVER_ERROR.getCode();
    }

    public ExcelException(Throwable cause) {
        super(cause);
        this.code = ResultCode.INTERNAL_SERVER_ERROR.getCode();
    }

    public long getCode() {
        return code;
    }
}
