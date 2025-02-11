package com.scheduler.service.scheduler.dto;

import com.scheduler.service.scheduler.repository.pojo.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


public class ScheduleEventRequest {

	@NotNull(message = "Event type is required")
	private EventType eventType;

	@NotBlank(message = "Cron expression is required")
	private String cronExpression;

	@NotBlank(message = "Time zone is required")
	private String timeZone;

	@NotNull(message = "Total runs count is required")
	private Integer totalRuns;

	// API Call Event Specific
	private String apiUrl;
	private String httpMethod;
	private String payload;

	// Kafka Specific
	private String kafkaTopic;
	private String kafkaMessageKey;
	private String kafkaPayload;

	// RabbitMQ Specific
	private String rabbitMqExchange;
	private String rabbitMqRoutingKey;
	private String rabbitMqPayload;

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public Integer getTotalRuns() {
		return totalRuns;
	}

	public void setTotalRuns(Integer totalRuns) {
		this.totalRuns = totalRuns;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getKafkaTopic() {
		return kafkaTopic;
	}

	public void setKafkaTopic(String kafkaTopic) {
		this.kafkaTopic = kafkaTopic;
	}

	public String getKafkaMessageKey() {
		return kafkaMessageKey;
	}

	public void setKafkaMessageKey(String kafkaMessageKey) {
		this.kafkaMessageKey = kafkaMessageKey;
	}

	public String getKafkaPayload() {
		return kafkaPayload;
	}

	public void setKafkaPayload(String kafkaPayload) {
		this.kafkaPayload = kafkaPayload;
	}

	public String getRabbitMqExchange() {
		return rabbitMqExchange;
	}

	public void setRabbitMqExchange(String rabbitMqExchange) {
		this.rabbitMqExchange = rabbitMqExchange;
	}

	public String getRabbitMqRoutingKey() {
		return rabbitMqRoutingKey;
	}

	public void setRabbitMqRoutingKey(String rabbitMqRoutingKey) {
		this.rabbitMqRoutingKey = rabbitMqRoutingKey;
	}

	public String getRabbitMqPayload() {
		return rabbitMqPayload;
	}

	public void setRabbitMqPayload(String rabbitMqPayload) {
		this.rabbitMqPayload = rabbitMqPayload;
	}

	/*public ScheduledEvent toEntity() {
		ScheduledEvent event = new ScheduledEvent();
		event.setEventType(eventType);
		event.setCronExpression(cronExpression);
		event.setTimeZone(timeZone);
		event.setTotalRuns(totalRuns);

		switch (eventType) {
			case API_CALL:
				event.setApiUrl(apiUrl);
				event.setHttpMethod(httpMethod);
				event.setPayload(payload);
				break;
			case KAFKA:
				event.setKafkaTopic(kafkaTopic);
				event.setKafkaMessageKey(kafkaMessageKey);
				event.setKafkaPayload(kafkaPayload);
				break;
			case RABBITMQ:
				event.setRabbitMqExchange(rabbitMqExchange);
				event.setRabbitMqRoutingKey(rabbitMqRoutingKey);
				event.setRabbitMqPayload(rabbitMqPayload);
				break;
		}
		return event;
	}*/
}
