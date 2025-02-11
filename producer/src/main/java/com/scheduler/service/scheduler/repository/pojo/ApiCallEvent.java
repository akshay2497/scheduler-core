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
@Table(name = "api_call_events")
public class ApiCallEvent {

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
	@OneToMany(mappedBy = "apiCallEvent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EventSchedule> eventSchedules = new ArrayList<>();

	@Column(name = "api_url", nullable = false, columnDefinition = "LONGTEXT")
	private String apiUrl;

	@Column(name = "http_method", nullable = false)
	private String httpMethod;

	@Lob
	@Column(name = "payload", nullable = false, columnDefinition = "LONGTEXT")
	private String payload;

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
}
