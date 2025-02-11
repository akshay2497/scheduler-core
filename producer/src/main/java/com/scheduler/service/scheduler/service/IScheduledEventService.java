package com.scheduler.service.scheduler.service;

import com.scheduler.service.scheduler.dto.ScheduleEventRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface IScheduledEventService {

	// Generate schedule instances for the event based on cron expression
	void createSchedules(ScheduleEventRequest event, List<LocalDateTime> scheduleEventKeys);
}
