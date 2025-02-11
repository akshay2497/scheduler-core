package com.scheduler.service.scheduler.dto;

import lombok.Data;

@Data
public class KafkaEventDTO {
	private Long id;

	private String kafkaTopic;

	private String kafkaMessageKey;

	private String kafkaPayload;
}
