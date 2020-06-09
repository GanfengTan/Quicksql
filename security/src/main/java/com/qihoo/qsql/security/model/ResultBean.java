package com.qihoo.qsql.security.model;

import com.qihoo.qsql.security.common.ErrorCode;
import java.io.Serializable;


public class ResultBean<T> implements Serializable {

    private String code;
    private T data;
    private String message;

    public ResultBean(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultBean(String code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public ResultBean(ErrorCode error, T data) {
        this.code = error.code;
        this.message = error.message;
        this.data = data;
    }

    public ResultBean(ErrorCode error) {
        this.code = error.code;
        this.message = error.message;
        this.data = null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
