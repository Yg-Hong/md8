package com.ms8.md.tracking.feature.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerService {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void sendMessageToKafka(String topicName, String message) {
		log.info("Producer Message : {}", message);
		this.kafkaTemplate.send(topicName,message);
	}
	
}
