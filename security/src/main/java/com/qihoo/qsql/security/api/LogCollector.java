package com.qihoo.qsql.security.api;

import com.qihoo.qsql.security.config.KafkaConfig;
import com.qihoo.qsql.security.config.SingleInstanceFactory;

public class LogCollector {

    public void sendMessage(String topic, String message) {
        KafkaConfig kafkaConfig = SingleInstanceFactory.getKafkaConfigInstance();
        SingleInstanceFactory.getExecutorInstance().execute(new SendMessage(topic, message, kafkaConfig));

    }
}

class SendMessage implements Runnable {

    private String topic;
    private String message;
    private KafkaConfig kafkaConfig;

    public SendMessage(String topic, String message, KafkaConfig kafkaConfig) {
        this.topic = topic;
        this.message = message;
        this.kafkaConfig = kafkaConfig;
    }

    @Override
    public void run() {
        kafkaConfig.sendMsg(topic, message);
    }
}

