package org.auca.webtech.spms.repository;

import java.util.List;

import org.auca.webtech.spms.domain.Event;
import org.auca.webtech.spms.domain.PerformanceTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceTimeRepository extends JpaRepository<PerformanceTime, Integer> {

	List<PerformanceTime> findByEvent(Event event, Pageable pagination);

}