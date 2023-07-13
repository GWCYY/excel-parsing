package com.rookiesquad.excelparsing.exception;

public enum ExcelErrorCode {

    ILLEGAL_RECONCILIATION_TYPE(1000010001, "{ILLEGAL_RECONCILIATION_TYPE}", "非法的对账类型"),
    INVALID_FILE_PATH(1000010002, "{INVALID_FILE_PATH}", "无效的文件路径"),
    PARSING_FILE_FAILED(1000010003, "{PARSING_FILE_FAILED}", "文件解析失败"),
    WRITE_FILE_FAILED(1000010004, "{WRITE_FILE_FAILED}", "写入文件失败"),
    ;

    private final long code;
    private final String message;
    private final String description;

    ExcelErrorCode(long code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
