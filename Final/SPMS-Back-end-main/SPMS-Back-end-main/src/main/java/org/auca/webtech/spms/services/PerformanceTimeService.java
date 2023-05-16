package org.auca.webtech.spms.services;

import java.util.List;
import java.util.UUID;

import org.auca.webtech.spms.domain.PerformanceTime;

public interface PerformanceTimeService {
	PerformanceTime savePerformanceTime(PerformanceTime performanceTime, UUID artistId, UUID eventId) throws Exception;
	List<PerformanceTime> findPerformanceTimeByEvent(UUID eventId,int page,int size) throws Exception;
	void deletePerformanceTimeById(Integer id) throws Exception;
}