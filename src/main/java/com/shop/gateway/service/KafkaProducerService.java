package com.shop.gateway.service;

import com.shop.gateway.service.dto.ApiCall;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, ApiCall> kafkaTemplate;

    public void sendMessage(String topic, String id, String url) {
        ApiCall apiCall = ApiCall.builder()
                .url(url)
                .userId(id)
                .build();
        kafkaTemplate.send(topic, apiCall);
    }
}