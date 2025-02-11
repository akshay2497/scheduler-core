package com.scheduler.service.scheduler.service;

import com.scheduler.service.scheduler.dto.ApiCallEventDTO;
import com.scheduler.service.scheduler.dto.KafkaEventDTO;
import com.scheduler.service.scheduler.dto.RabbitMqEventDTO;
import com.scheduler.service.scheduler.repository.dao.EventScheduleRepository;
import com.scheduler.service.scheduler.repository.pojo.*;
import com.scheduler.service.scheduler.utils.TimeUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@Transactional
public class ProducerServiceImpl implements IProducerService{

	@Value("${api-topic}")
	private String apiTopic;

	@Value("${kafka-topic}")
	private String kafkaTopic;

	@Value("${rabbit-mq-topic}")
	private String rabbitMqTopic;

	@Autowired
	private KafkaTemplate<String, Object> template;

	@Autowired
	private EventScheduleRepository eventScheduleRepository;

	@Override
	@Scheduled(initialDelay = 1000, fixedRate = 1000000)
	public void produceApiCallEvents() {

		LocalDateTime currentDateTimeInUTC = LocalDateTime.now(ZoneOffset.UTC);
		LocalDateTime hashKey =
			TimeUtils.roundToNearestTenMinutes(currentDateTimeInUTC);

		System.out.println(hashKey.toString());

		List<EventSchedule> eventSchedules =
			eventScheduleRepository.findPendingSchedulesByCurrentTime(
				hashKey, EventType.API_CALL).orElse(null);

		if (eventSchedules == null || eventSchedules.isEmpty()) {
			return;
		}

		for (EventSchedule eventSchedule : eventSchedules) {

			ApiCallEvent apiCallEvent = eventSchedule.getApiCallEvent();
			ApiCallEventDTO apiCallEventDTO = new ApiCallEventDTO();

			BeanUtils.copyProperties(apiCallEvent, apiCallEventDTO);

			template.send(apiTopic, apiCallEventDTO);

			eventSchedule.setStatus(ScheduleStatus.IN_PROGRESS);

			eventScheduleRepository.save(eventSchedule);
		}
	}

	@Override
	@Scheduled(initialDelay = 1000, fixedRate = 1000000)
	public void produceKafkaEvents() {
		LocalDateTime currentDateTimeInUTC = LocalDateTime.now(ZoneOffset.UTC);
		LocalDateTime hashKey =
			TimeUtils.roundToNearestTenMinutes(currentDateTimeInUTC);

		List<EventSchedule> eventSchedules =
			eventScheduleRepository.findPendingSchedulesByCurrentTime(
				hashKey, EventType.KAFKA).orElse(null);

		if (eventSchedules == null || eventSchedules.isEmpty()) {
			return;
		}

		for (EventSchedule eventSchedule : eventSchedules) {

			KafkaEvent kafkaEvent = eventSchedule.getKafkaEvent();
			KafkaEventDTO kafkaEventDTO = new KafkaEventDTO();

			BeanUtils.copyProperties(kafkaEvent, kafkaEventDTO);

			template.send(kafkaTopic, kafkaEventDTO);

			eventSchedule.setStatus(ScheduleStatus.IN_PROGRESS);

			eventScheduleRepository.save(eventSchedule);
		}
	}

	@Override
	@Scheduled(initialDelay = 1000, fixedRate = 1000000)
	public void produceRabbitMqEvents() {
		LocalDateTime currentDateTimeInUTC = LocalDateTime.now(ZoneOffset.UTC);
		LocalDateTime hashKey =
			TimeUtils.roundToNearestTenMinutes(currentDateTimeInUTC);

		List<EventSchedule> eventSchedules =
			eventScheduleRepository.findPendingSchedulesByCurrentTime(
				hashKey, EventType.RABBITMQ).orElse(null);

		if (eventSchedules == null || eventSchedules.isEmpty()) {
			return;
		}

		for (EventSchedule eventSchedule : eventSchedules) {

			RabbitMqEvent rabbitMqEvent = eventSchedule.getRabbitMqEvent();
			RabbitMqEventDTO rabbitMqEventDTO = new RabbitMqEventDTO();

			BeanUtils.copyProperties(rabbitMqEvent, rabbitMqEventDTO);

			template.send(rabbitMqTopic, rabbitMqEventDTO);

			eventSchedule.setStatus(ScheduleStatus.IN_PROGRESS);

			eventScheduleRepository.save(eventSchedule);
		}
	}
}
