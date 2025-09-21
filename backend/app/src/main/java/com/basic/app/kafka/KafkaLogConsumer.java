package com.basic.app.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.basic.app.entity.LogApi;
import com.basic.app.service.interfaces.LogService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : KafkaLogConsumer.java
 * @설명 : Kafka 로그 메시지 소비자 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Log4j2
@Component
public class KafkaLogConsumer {

    @Autowired
    private LogService logService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Consumer<String, String> consumer;

    /**
     * @기능 : KafkaLogConsumer 생성자
     * @param kafkaConsumerProperties Kafka 소비자 설정
     */
    public KafkaLogConsumer(Properties kafkaConsumerProperties) {
        this.consumer = new KafkaConsumer<>(kafkaConsumerProperties);
        consumer.subscribe(Collections.singletonList("log-topic")); // Topic 지정
    }

    /**
     * @기능 : Kafka 메시지 수신 및 처리
     */
    @Scheduled(fixedDelay = 10000)
    public void listen() {
        try {

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

            List<LogApi> logApis = new ArrayList<>();

            for (ConsumerRecord<String, String> record : records) {
                String jsonMessage = record.value();
                try {
                    LogApi logApi = objectMapper.readValue(jsonMessage, LogApi.class);
                    if (logApi != null) {
                        logApis.add(logApi);
                    }
                } catch (Exception e) {
                    log.warn("CBSK Kafka : 역직렬화 실패: {}", record.value(), e);
                }
            }
            log.info("CBSK Kafka : {}건 로그 수신.", logApis.size());

            if (!logApis.isEmpty()) {
                logService.insertApiLog(logApis);
                log.info("CBSK Kafka : 총 {}건 로그 저장 완료.", logApis.size());
            }

            consumer.commitSync();
        } catch (Exception e) {
            log.error("Kafka 로그 소비 중 오류 발생", e);
        }
    }
}