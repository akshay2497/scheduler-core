package com.scheduler.service.scheduler.service;

import com.scheduler.service.scheduler.dto.ScheduleEventRequest;
import com.scheduler.service.scheduler.repository.dao.ApiCallEventRepository;
import com.scheduler.service.scheduler.repository.dao.EventScheduleRepository;
import com.scheduler.service.scheduler.repository.pojo.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ApiCallEventServiceImpl implements IScheduledEventService {
	private final ApiCallEventRepository apiCallEventRepository;
	private final EventScheduleRepository eventScheduleRepository;

	@Autowired
	public ApiCallEventServiceImpl(ApiCallEventRepository apiCallEventRepository, EventScheduleRepository eventScheduleRepository) {
		this.apiCallEventRepository = apiCallEventRepository;
		this.eventScheduleRepository = eventScheduleRepository;
	}

	@Override
	public void createSchedules(ScheduleEventRequest event,
								List<LocalDateTime> scheduleEventKeys) {
		ApiCallEvent eventObj = createEventObj(event);

		ApiCallEvent savedEntity = apiCallEventRepository.save(eventObj);

		/**
		 * Add all schedules to the EventSchedule table
		 */

		for (LocalDateTime key : scheduleEventKeys) {
			EventSchedule eventSchedule = new EventSchedule();
			eventSchedule.setScheduledTime(key);
			eventSchedule.setApiCallEvent(savedEntity);
			eventSchedule.setEventType(EventType.API_CALL);
			eventScheduleRepository.save(eventSchedule);
		}
	}

	private ApiCallEvent createEventObj(ScheduleEventRequest event) {
		ApiCallEvent apiCallEvent = new ApiCallEvent();
		apiCallEvent.setCronExpression(event.getCronExpression());
		apiCallEvent.setTotalRuns(event.getTotalRuns());
		apiCallEvent.setApiUrl(event.getApiUrl());
		apiCallEvent.setHttpMethod(event.getHttpMethod());
		apiCallEvent.setPayload(event.getPayload());

		return apiCallEvent;
	}
}
