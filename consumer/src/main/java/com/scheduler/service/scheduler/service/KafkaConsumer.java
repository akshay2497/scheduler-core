package com.scheduler.service.scheduler.service;

import com.scheduler.service.scheduler.dto.ApiCallEventDTO;
import com.scheduler.service.scheduler.dto.KafkaEventDTO;
import com.scheduler.service.scheduler.dto.RabbitMqEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

	Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

	@KafkaListener(topics = "api-topic", groupId = "scheduler-consumer-group")
	public void consumeData(ApiCallEventDTO apiCallEventDTO, Acknowledgment acknowledgment) {
		/*logger.info("Consumer1 consumed the message : " + apiCallEventDTO.toString());
		acknowledgment.acknowledge();*/
		//TODO
		//Publish the api call events ad update the schedule state in scheduledEvents table
	}

	@KafkaListener(topics = "kafka-topic", groupId = "scheduler-consumer-group")
	public void consumeData(KafkaEventDTO kafkaEventDTO, Acknowledgment acknowledgment) {
		/*logger.info("Consumer2 consumed the message : " + kafkaEventDTO.toString());
		acknowledgment.acknowledge();*/
		//TODO
		//Publish the kafka events ad update the schedule state in scheduledEvents table
	}

	@KafkaListener(topics = "rabbit-mq-topic", groupId = "scheduler-consumer-group")
	public void consumeData(RabbitMqEventDTO rabbitMqEventDTO, Acknowledgment acknowledgment) {
		/*logger.info("Consumer3 consumed the message : " + rabbitMqEventDTO.toString());
		acknowledgment.acknowledge();*/
		//TODO
		//Publish the rabbit mq events ad update the schedule state in scheduledEvents table
	}
}
