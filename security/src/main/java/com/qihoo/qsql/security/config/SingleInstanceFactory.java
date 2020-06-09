package com.qihoo.qsql.security.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleInstanceFactory {

    private static ExecutorService executorInstance;

    private static KafkaConfig kafkaConfigInstance;

    public static synchronized ExecutorService getExecutorInstance() {
        if (executorInstance == null) {
            executorInstance = Executors.newSingleThreadExecutor();
        }
        return executorInstance;
    }

    public static synchronized KafkaConfig getKafkaConfigInstance() {
        if (kafkaConfigInstance == null) {
            kafkaConfigInstance = new KafkaConfig();
        }
        return kafkaConfigInstance;
    }



}
