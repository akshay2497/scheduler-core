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
+-----------------+--------------+------+-----+---------+----------------+
| Field           | Type         | Null | Key | Default | Extra          |
+-----------------+--------------+------+-----+---------+----------------+
| id              | bigint       | NO   | PRI | NULL    | auto_increment |
| api_url         | longtext     | NO   |     | NULL    |                |
| created_at      | datetime(6)  | YES  |     | NULL    |                |
| cron_expression | varchar(255) | NO   |     | NULL    |                |
| http_method     | varchar(255) | NO   |     | NULL    |                |
| payload         | longtext     | NO   |     | NULL    |                |
| total_runs      | int          | NO   |     | NULL    |                |
| updated_at      | datetime(6)  | YES  |     | NULL    |                |
+-----------------+--------------+------+-----+---------+----------------+

kafka_events;
+-------------------+--------------+------+-----+---------+----------------+
| Field             | Type         | Null | Key | Default | Extra          |
+-------------------+--------------+------+-----+---------+----------------+
| id                | bigint       | NO   | PRI | NULL    | auto_increment |
| created_at        | datetime(6)  | YES  |     | NULL    |                |
| cron_expression   | varchar(255) | NO   |     | NULL    |                |
| kafka_message_key | varchar(255) | NO   |     | NULL    |                |
| kafka_payload     | longtext     | NO   |     | NULL    |                |
| kafka_topic       | varchar(255) | NO   |     | NULL    |                |
| total_runs        | int          | NO   |     | NULL    |                |
| updated_at        | datetime(6)  | YES  |     | NULL    |                |
+-------------------+--------------+------+-----+---------+----------------+

rabbitmq_events;
+-----------------------+--------------+------+-----+---------+----------------+
| Field                 | Type         | Null | Key | Default | Extra          |
+-----------------------+--------------+------+-----+---------+----------------+
| id                    | bigint       | NO   | PRI | NULL    | auto_increment |
| created_at            | datetime(6)  | YES  |     | NULL    |                |
| cron_expression       | varchar(255) | NO   |     | NULL    |                |
| rabbit_mq_exchange    | varchar(255) | NO   |     | NULL    |                |
| rabbit_mq_payload     | longtext     | NO   |     | NULL    |                |
| rabbit_mq_routing_key | varchar(255) | NO   |     | NULL    |                |
| total_runs            | int          | NO   |     | NULL    |                |
| updated_at            | datetime(6)  | YES  |     | NULL    |                |
+-----------------------+--------------+------+-----+---------+----------------+


event_schedules;
+--------------------+----------------------------------------------------+------+-----+---------+----------------+
| Field              | Type                                               | Null | Key | Default | Extra          |
+--------------------+----------------------------------------------------+------+-----+---------+----------------+
| id                 | bigint                                             | NO   | PRI | NULL    | auto_increment |
| created_at         | datetime(6)                                        | YES  |     | NULL    |                |
| error_message      | tinytext                                           | YES  |     | NULL    |                |
| event_type         | enum('API_CALL','KAFKA','RABBITMQ')                | NO   |     | NULL    |                |
| executed_at        | datetime(6)                                        | YES  |     | NULL    |                |
| retry_count        | int                                                | YES  |     | NULL    |                |
| scheduled_time     | datetime(6)                                        | NO   |     | NULL    |                |
| status             | enum('COMPLETED','FAILED','IN_PROGRESS','PENDING') | NO   |     | NULL    |                |
| updated_at         | datetime(6)                                        | YES  |     | NULL    |                |
| api_call_event_id  | bigint                                             | YES  | MUL | NULL    |                |
| kafka_event_id     | bigint                                             | YES  | MUL | NULL    |                |
| rabbit_mq_event_id | bigint                                             | YES  | MUL | NULL    |                |
+--------------------+----------------------------------------------------+------+-----+---------+----------------+


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



