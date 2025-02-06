# scheduler-core
Centralized scalable scheduler service

Question

You have N number of Microservices
They are emitting some events -
1. API Call Event - to be executed - Once/Weekly/Monthly - How many times
2. Kafka Event - to be executed - Once/Weekly/Monthly- How many times
3. RabbitMQ Event - to be executed - Once/Weekly/Monthly- How many times

you have to Design a scheduler service for doing to solve this problem


Solution

1. High-level design is specified in the hld.pdf file.
2. Database schema

api_call_events;

```SQL
CREATE TABLE `api_call_events` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `api_url` longtext NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `cron_expression` varchar(255) NOT NULL,
  `http_method` varchar(255) NOT NULL,
  `payload` longtext NOT NULL,
  `total_runs` int NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```

kafka_events;

```SQL
CREATE TABLE `kafka_events` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `cron_expression` varchar(255) NOT NULL,
  `kafka_message_key` varchar(255) NOT NULL,
  `kafka_payload` longtext NOT NULL,
  `kafka_topic` varchar(255) NOT NULL,
  `total_runs` int NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```

rabbitmq_events;

```SQL
CREATE TABLE `rabbitmq_events` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `cron_expression` varchar(255) NOT NULL,
  `rabbit_mq_exchange` varchar(255) NOT NULL,
  `rabbit_mq_payload` longtext NOT NULL,
  `rabbit_mq_routing_key` varchar(255) NOT NULL,
  `total_runs` int NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```

event_schedules;

```SQL
CREATE TABLE `event_schedules` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `error_message` tinytext,
  `event_type` enum('API_CALL','KAFKA','RABBITMQ') NOT NULL,
  `executed_at` datetime(6) DEFAULT NULL,
  `retry_count` int DEFAULT NULL,
  `scheduled_time` datetime(6) NOT NULL,
  `status` enum('COMPLETED','FAILED','IN_PROGRESS','PENDING') NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `api_call_event_id` bigint DEFAULT NULL,
  `kafka_event_id` bigint DEFAULT NULL,
  `rabbit_mq_event_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrkfx6ccyblhiyxx4tcvrkh82i` (`api_call_event_id`),
  KEY `FKlmwktk502n3b4sy7rw1y596wc` (`kafka_event_id`),
  KEY `FKkcubd93l99r5kmey3hp9877eu` (`rabbit_mq_event_id`),
  CONSTRAINT `FKkcubd93l99r5kmey3hp9877eu` FOREIGN KEY (`rabbit_mq_event_id`) REFERENCES `rabbitmq_events` (`id`),
  CONSTRAINT `FKlmwktk502n3b4sy7rw1y596wc` FOREIGN KEY (`kafka_event_id`) REFERENCES `kafka_events` (`id`),
  CONSTRAINT `FKrkfx6ccyblhiyxx4tcvrkh82i` FOREIGN KEY (`api_call_event_id`) REFERENCES `api_call_events` (`id`)
);
```


Producer service :
1. The producer will expose the API to receive the events from different microservices and store them in the database.
2. This will have 3 spring schedulers acting as producers running every 10 minutes which will produce different events to kafka queue.


Consumer service :
1. It will have 3 KafkaListenners to listen to 3 different event types from the queue and execute them.

Producer API CURL'S :

1. API EVENT
curl --location 'localhost:9001/core/api/v1/scheduler/schedule' \
--header 'Content-Type: application/json' \
--data '{
    "eventType": "API_CALL",
    "cronExpression": "0 0 10 ? * MON",
    "timeZone": "UTC",
    "totalRuns": 10,
    "apiUrl": "https://example.com/webhook",
    "httpMethod": "POST",
    "payload": "{\"key\":\"value\"}"
}
'

2. Kafka Event
curl --location 'localhost:9001/core/api/v1/scheduler/schedule' \
--header 'Content-Type: application/json' \
--data '{
    "eventType": "KAFKA",
    "cronExpression": "0 0 10 ? * TUE",
    "timeZone": "UTC",
    "totalRuns": 10,
    "kafkaTopic": "orders",
    "kafkaMessageKey": "order-123",
    "kafkaPayload": "{\"order_id\": 123}"
}
'

3. RabbitMq Event
curl --location 'localhost:9001/core/api/v1/scheduler/schedule' \
--header 'Content-Type: application/json' \
--data '{
    "eventType": "RABBITMQ",
    "cronExpression": "0 0 10 ? * FRI",
    "timeZone": "UTC",
    "totalRuns": 10,
    "rabbitMqExchange": "order_exchange",
    "rabbitMqRoutingKey": "order.created",
    "rabbitMqPayload": "{\"order_id\": 123}"
}
'



