package com.scheduler.service.scheduler.dto;


import lombok.Data;

@Data
public class ApiCallEventDTO {
	private Long id;

	private String apiUrl;

	private String httpMethod;

	private String payload;
}
