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
@Table(name = "kafka_events")
public class KafkaEvent {

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
	@OneToMany(mappedBy = "kafkaEvent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EventSchedule> eventSchedules = new ArrayList<>();

	@Column(name = "kafka_topic", nullable = false)
	private String kafkaTopic;

	@Column(name = "kafka_message_key", nullable = false)
	private String kafkaMessageKey;

	@Column(name = "kafka_payload", nullable = false, columnDefinition = "LONGTEXT")
	@Lob
	private String kafkaPayload;

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
}
