package com.ms8.md.tracking.feature.kafka.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms8.md.tracking.feature.kafka.service.KafkaProducerService;
import com.ms8.md.tracking.global.common.code.SuccessCode;
import com.ms8.md.tracking.global.common.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/kafka")
@RestController
public class KafkaProducerController {

	private final KafkaProducerService kafkaProducerService;

	@PostMapping
	public SuccessResponse<?> sendMassage(@RequestParam(name = "topicName") String topicName, @RequestParam(name="message") String message) {
		this.kafkaProducerService.sendMessageToKafka(topicName, message);
		return SuccessResponse.builder().data(null).status(SuccessCode.SEND_MESSAGE_SUCCESS).build();
	}

}
