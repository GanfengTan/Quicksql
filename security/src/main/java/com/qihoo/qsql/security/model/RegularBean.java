package com.qihoo.qsql.security.model;


public class RegularBean {

    public String condition;
    public Integer status;
    public Integer level;
    private String function;
    private Integer include;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Integer getInclude() {
        return include;
    }

    public void setInclude(Integer include) {
        this.include = include;
    }
}
