package com.scheduler.service.scheduler.dto;

import lombok.Data;

@Data
public class RabbitMqEventDTO {

	private Long id;

	private String rabbitMqExchange;

	private String rabbitMqRoutingKey;

	private String rabbitMqPayload;
}
