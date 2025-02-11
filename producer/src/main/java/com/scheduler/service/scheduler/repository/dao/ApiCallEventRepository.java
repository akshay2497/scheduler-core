package com.scheduler.service.scheduler.repository.dao;

import com.scheduler.service.scheduler.repository.pojo.ApiCallEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiCallEventRepository extends JpaRepository<ApiCallEvent, Long> {
}
