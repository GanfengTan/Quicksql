package com.qihoo.qsql.security.common;

/**
 * 分析引擎配置参数定义.
 */
public enum CommonConfiguration {
    security_config_file("security-config.json"),
    kafka_topic_key("kafka.submit.topic.name"),
    kafka_properties("kafka.properties");


    private String value;

    CommonConfiguration(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
