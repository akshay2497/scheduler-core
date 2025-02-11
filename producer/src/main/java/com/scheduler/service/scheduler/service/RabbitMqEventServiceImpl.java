package com.scheduler.service.scheduler.service;

import com.scheduler.service.scheduler.dto.ScheduleEventRequest;
import com.scheduler.service.scheduler.repository.dao.EventScheduleRepository;
import com.scheduler.service.scheduler.repository.dao.RabbitMqEventRepository;
import com.scheduler.service.scheduler.repository.pojo.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RabbitMqEventServiceImpl implements IScheduledEventService {

	private final RabbitMqEventRepository rabbitMqEventRepository;
	private final EventScheduleRepository eventScheduleRepository;

	@Autowired
	public RabbitMqEventServiceImpl(RabbitMqEventRepository rabbitMqEventRepository, EventScheduleRepository eventScheduleRepository) {
		this.rabbitMqEventRepository = rabbitMqEventRepository;
		this.eventScheduleRepository = eventScheduleRepository;
	}

	@Override
	public void createSchedules(ScheduleEventRequest event,
								List<LocalDateTime> scheduleEventKeys) {
		RabbitMqEvent rabbitMqEvent = createEventObj(event);

		RabbitMqEvent savedEvent = rabbitMqEventRepository.save(rabbitMqEvent);

		/**
		 * Add all schedules to the EventSchedule table
		 */

		for (LocalDateTime key : scheduleEventKeys) {
			EventSchedule eventSchedule = new EventSchedule();
			eventSchedule.setScheduledTime(key);
			eventSchedule.setRabbitMqEvent(savedEvent);
			eventSchedule.setEventType(EventType.RABBITMQ);
			eventScheduleRepository.save(eventSchedule);
		}
	}

	private RabbitMqEvent createEventObj(ScheduleEventRequest event) {
		RabbitMqEvent rabbitMqEvent = new RabbitMqEvent();
		rabbitMqEvent.setCronExpression(event.getCronExpression());
		rabbitMqEvent.setTotalRuns(event.getTotalRuns());
		rabbitMqEvent.setRabbitMqExchange(event.getRabbitMqExchange());
		rabbitMqEvent.setRabbitMqRoutingKey(event.getRabbitMqRoutingKey());
		rabbitMqEvent.setRabbitMqPayload(event.getRabbitMqPayload());
		return rabbitMqEvent;
	}
}
