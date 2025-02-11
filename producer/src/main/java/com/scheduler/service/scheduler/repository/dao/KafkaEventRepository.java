package com.scheduler.service.scheduler.repository.dao;

import com.scheduler.service.scheduler.repository.pojo.KafkaEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaEventRepository extends JpaRepository<KafkaEvent, Long> {

}
