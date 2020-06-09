package com.qihoo.qsql.security;

import com.qihoo.qsql.security.common.CommonConfiguration;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


public class KafkaConsume {

    private static Properties properties;

    static {
        properties = new Properties();
        try {
            InputStream fis = KafkaConsume.class.getClassLoader().getResourceAsStream(
                CommonConfiguration.kafka_properties.getValue());
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取消息
     */
    public void getMsg() throws Exception {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer
            .subscribe(Collections.singletonList((String) properties
                .get(CommonConfiguration.kafka_topic_key.getValue())));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> str : records
                    ) {
                    System.out.println(str.value());
                }

            }
        } finally {
            consumer.close();
        }
    }

}
