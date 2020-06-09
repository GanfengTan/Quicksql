package com.qihoo.qsql.security.common;

public enum ErrorCode {

    NO_ERROR("1", "success"),
    ERROR("0", "fail");

    public final String message;
    public final String code;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
