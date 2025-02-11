package com.scheduler.service.scheduler.repository.pojo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_schedules")
public class EventSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Many-to-one relationship with ScheduledEvent
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "api_call_event_id")
	private ApiCallEvent apiCallEvent;

	// Many-to-one relationship with ScheduledEvent
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kafka_event_id")
	private KafkaEvent kafkaEvent;

	// Many-to-one relationship with ScheduledEvent
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rabbit_mq_event_id")
	private RabbitMqEvent rabbitMqEvent;

	@Column(name = "scheduled_time", nullable = false)
	private LocalDateTime scheduledTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private ScheduleStatus status = ScheduleStatus.PENDING;

	@Enumerated(EnumType.STRING)
	@Column(name = "event_type", nullable = false)
	private EventType eventType;

	@Column(name = "executed_at")
	private LocalDateTime executedAt;

	@Column(name = "retry_count")
	private Integer retryCount = 0;

	@Lob
	@Column(name = "error_message")
	private String errorMessage;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ApiCallEvent getApiCallEvent() {
		return apiCallEvent;
	}

	public void setApiCallEvent(ApiCallEvent apiCallEvent) {
		this.apiCallEvent = apiCallEvent;
	}

	public KafkaEvent getKafkaEvent() {
		return kafkaEvent;
	}

	public void setKafkaEvent(KafkaEvent kafkaEvent) {
		this.kafkaEvent = kafkaEvent;
	}

	public RabbitMqEvent getRabbitMqEvent() {
		return rabbitMqEvent;
	}

	public void setRabbitMqEvent(RabbitMqEvent rabbitMqEvent) {
		this.rabbitMqEvent = rabbitMqEvent;
	}

	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public ScheduleStatus getStatus() {
		return status;
	}

	public void setStatus(ScheduleStatus status) {
		this.status = status;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public LocalDateTime getExecutedAt() {
		return executedAt;
	}

	public void setExecutedAt(LocalDateTime executedAt) {
		this.executedAt = executedAt;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
