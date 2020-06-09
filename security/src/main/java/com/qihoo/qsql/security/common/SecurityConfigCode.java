package com.qihoo.qsql.security.common;

public enum SecurityConfigCode {
    KEYWORDS("keyWords"),DDL("ddl"),CREATE("create"),TABLE("table"),VIEW("view"),INDEX("index")
    ,SYN("syn"),CLUSTER("cluster"),DATABASE("database"),DESC("desc"),SHOW("show"),DROP("drop")
    ,TRUNCATE("truncate"),ALER_TABLE("aler table"),MODIFY("modify"),ADD("add"),CHANGE("change")
    ,DESCRIBE("describe"),INSERT("insert"),DELETE("delete"),UPDATE("update"),SELECT("select"),DML("dml")
    ,RULES("rules"),JOIN("join"),SYMBOL_ALL("*"),ORDER_BY("order by"),LIMIT("limit"),REGULAR("regular")
    ,REGULARS("regulars"),SYMBOL("symbol"),CONDITION("condition"),INCLUDE("include"),STATUS("status")
    ,LEVEL("level"),FUNCTION("function"),KEYNUMBER("keyNumber"),SUBQUERY("subquery"),VALUE("value");

    public final String code;

    SecurityConfigCode(String code){
        this.code = code;
    }
}
