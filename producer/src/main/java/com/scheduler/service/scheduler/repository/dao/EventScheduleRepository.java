package com.scheduler.service.scheduler.repository.dao;

import com.scheduler.service.scheduler.repository.pojo.EventSchedule;
import com.scheduler.service.scheduler.repository.pojo.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventScheduleRepository extends JpaRepository<EventSchedule, Long> {
	@Query("SELECT es FROM EventSchedule es WHERE es.scheduledTime = :currentTime AND es.status = 'PENDING' AND es.eventType = :eventType")
	Optional<List<EventSchedule>> findPendingSchedulesByCurrentTime(LocalDateTime currentTime, EventType eventType);
}
