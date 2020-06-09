package com.qihoo.qsql.security.config;

import com.qihoo.qsql.security.common.CommonConfiguration;
import java.io.InputStream;
import java.util.Properties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class KafkaConfig {

    private static Properties properties;

    static {
        properties = new Properties();
        try {
            InputStream fis = KafkaProducer.class.getClassLoader().getResourceAsStream(
                CommonConfiguration.kafka_properties.getValue());
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     */
    public void sendMsg(String topic, String data) {

        // 实例化produce
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

        // 消息封装
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(
            topic, data);

        // 发送数据
        kafkaProducer.send(producerRecord, new Callback() {
            // 回调函数
            @Override
            public void onCompletion(RecordMetadata metadata,
                Exception exception) {
                if (null != exception) {
                    exception.printStackTrace();
                    throw new RuntimeException(exception);
                }
            }
        });

        // 关闭produce
        kafkaProducer.close();
    }
}


