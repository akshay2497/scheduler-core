package com.scheduler.service.scheduler.service;

import com.scheduler.service.scheduler.dto.ScheduleEventRequest;
import com.scheduler.service.scheduler.repository.dao.EventScheduleRepository;
import com.scheduler.service.scheduler.repository.dao.KafkaEventRepository;
import com.scheduler.service.scheduler.repository.pojo.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class KafkaEventServiceImpl implements IScheduledEventService {

	private final KafkaEventRepository kafkaEventRepository;
	private final EventScheduleRepository eventScheduleRepository;

	@Autowired
	public KafkaEventServiceImpl(KafkaEventRepository kafkaEventRepository, EventScheduleRepository eventScheduleRepository) {
		this.kafkaEventRepository = kafkaEventRepository;
		this.eventScheduleRepository = eventScheduleRepository;
	}

	@Override
	public void createSchedules(ScheduleEventRequest event,
								List<LocalDateTime> scheduleEventKeys) {
		KafkaEvent eventObj = createEventObj(event);

		KafkaEvent savedEvent = kafkaEventRepository.save(eventObj);

		/**
		 * Add all schedules to the EventSchedule table
		 */

		for (LocalDateTime key : scheduleEventKeys) {
			EventSchedule eventSchedule = new EventSchedule();
			eventSchedule.setScheduledTime(key);
			eventSchedule.setKafkaEvent(savedEvent);
			eventSchedule.setEventType(EventType.KAFKA);
			eventScheduleRepository.save(eventSchedule);
		}
	}

	private KafkaEvent createEventObj(ScheduleEventRequest event) {
		KafkaEvent kafkaEvent = new KafkaEvent();
		kafkaEvent.setCronExpression(event.getCronExpression());
		kafkaEvent.setTotalRuns(event.getTotalRuns());
		kafkaEvent.setKafkaTopic(event.getKafkaTopic());
		kafkaEvent.setKafkaMessageKey(event.getKafkaMessageKey());
		kafkaEvent.setKafkaPayload(event.getKafkaPayload());

		return kafkaEvent;
	}
}
