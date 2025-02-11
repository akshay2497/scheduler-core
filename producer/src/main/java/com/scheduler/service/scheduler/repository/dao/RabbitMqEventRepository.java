package com.scheduler.service.scheduler.repository.dao;

import com.scheduler.service.scheduler.repository.pojo.RabbitMqEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RabbitMqEventRepository  extends JpaRepository<RabbitMqEvent, Long> {
}
