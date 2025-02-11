package com.scheduler.service.scheduler.factory;

import com.scheduler.service.scheduler.repository.pojo.EventType;
import com.scheduler.service.scheduler.service.ApiCallEventServiceImpl;
import com.scheduler.service.scheduler.service.IScheduledEventService;
import com.scheduler.service.scheduler.service.KafkaEventServiceImpl;
import com.scheduler.service.scheduler.service.RabbitMqEventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventSchedulerFactory {

	private final Map<EventType, IScheduledEventService> eventServiceMap;

	@Autowired
	public EventSchedulerFactory(ApiCallEventServiceImpl apiCallEventService, KafkaEventServiceImpl kafkaEventService, RabbitMqEventServiceImpl rabbitMqEventService) {
		this.eventServiceMap = Map.of(
			EventType.API_CALL, apiCallEventService,
			EventType.KAFKA, kafkaEventService,
			EventType.RABBITMQ, rabbitMqEventService
		);
	}

	public IScheduledEventService getService(EventType eventType) {
		return eventServiceMap.get(eventType);
	}
}