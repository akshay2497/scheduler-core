package com.scheduler.service.scheduler.service;

public interface IProducerService {
	void produceApiCallEvents();
	void produceKafkaEvents();
	void produceRabbitMqEvents();
}
