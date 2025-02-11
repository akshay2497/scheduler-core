package com.scheduler.service.scheduler.controller;


import com.scheduler.service.scheduler.dto.ScheduleEventRequest;
import com.scheduler.service.scheduler.service.EventSchedulerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core/api/v1/scheduler")
public class SchedulerController {
	private final EventSchedulerService eventSchedulerService;

	@Autowired
	public SchedulerController(EventSchedulerService eventSchedulerService) {
		this.eventSchedulerService = eventSchedulerService;
	}

	@PostMapping("/schedule")
	public ResponseEntity<?> scheduleEvent(@Valid @RequestBody ScheduleEventRequest request) {
		eventSchedulerService.scheduleEvent(request);
		return ResponseEntity.ok("OK");
	}
}
