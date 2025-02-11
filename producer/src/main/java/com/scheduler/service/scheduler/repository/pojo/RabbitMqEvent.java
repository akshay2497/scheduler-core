package com.scheduler.service.scheduler.repository.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rabbitmq_events")
public class RabbitMqEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cron_expression", nullable = false)
	private String cronExpression;

	@Column(name = "total_runs", nullable = false)
	private Integer totalRuns;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	// One-to-many relationship with EventSchedule
	@OneToMany(mappedBy = "rabbitMqEvent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EventSchedule> eventSchedules = new ArrayList<>();

	@Column(name = "rabbit_mq_exchange", nullable = false)
	private String rabbitMqExchange;

	@Column(name = "rabbit_mq_routing_key", nullable = false)
	private String rabbitMqRoutingKey;

	@Column(name = "rabbit_mq_payload", nullable = false, columnDefinition = "LONGTEXT")
	@Lob
	private String rabbitMqPayload;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Integer getTotalRuns() {
		return totalRuns;
	}

	public void setTotalRuns(Integer totalRuns) {
		this.totalRuns = totalRuns;
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

	public List<EventSchedule> getEventSchedules() {
		return eventSchedules;
	}

	public void setEventSchedules(List<EventSchedule> eventSchedules) {
		this.eventSchedules = eventSchedules;
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
}
