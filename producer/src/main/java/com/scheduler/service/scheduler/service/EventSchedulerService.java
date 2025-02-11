package com.scheduler.service.scheduler.service;

import com.scheduler.service.scheduler.dto.ScheduleEventRequest;
import com.scheduler.service.scheduler.factory.EventSchedulerFactory;
import com.scheduler.service.scheduler.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EventSchedulerService {
	private final EventSchedulerFactory eventSchedulerFactory;

	@Autowired
	public EventSchedulerService(EventSchedulerFactory eventSchedulerFactory) {
		this.eventSchedulerFactory = eventSchedulerFactory;
	}

	public void scheduleEvent(ScheduleEventRequest request) {
		IScheduledEventService eventService =
			eventSchedulerFactory.getService(request.getEventType());

		String cronExpression = request.getCronExpression();
		Integer totalRuns = request.getTotalRuns();

		List<LocalDateTime> scheduleEventKeys =
			TimeUtils.createScheduleEventKeys(cronExpression, totalRuns);

		eventService.createSchedules(request, scheduleEventKeys);
	}
}
