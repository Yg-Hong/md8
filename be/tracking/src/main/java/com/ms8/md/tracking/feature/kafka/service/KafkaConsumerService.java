package com.ms8.md.tracking.feature.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaConsumerService {

	@KafkaListener(topics = "topicName")
	public void consume(String message) {
		log.info("Consumed Message : {}", message);
	}
}
