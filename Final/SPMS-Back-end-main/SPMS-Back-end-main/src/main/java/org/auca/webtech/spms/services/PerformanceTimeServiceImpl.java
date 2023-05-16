package org.auca.webtech.spms.services;

import java.util.List;
import java.util.UUID;

import org.auca.webtech.spms.domain.Artist;
import org.auca.webtech.spms.domain.Event;
import org.auca.webtech.spms.domain.PerformanceTime;
import org.auca.webtech.spms.repository.ArtistRepository;
import org.auca.webtech.spms.repository.EventRepository;
import org.auca.webtech.spms.repository.PerformanceTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PerformanceTimeServiceImpl implements PerformanceTimeService {
	
	@Autowired
	private PerformanceTimeRepository timeRepository;
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Override
	public PerformanceTime savePerformanceTime(PerformanceTime performanceTime, UUID artistId, UUID eventId) throws Exception {
		try {
			
			Artist artist = artistRepository.findById(artistId).orElseThrow(()->new Exception("Artist not Found"));
			Event event = eventRepository.findById(eventId).orElseThrow(()->new Exception("Event not found"));	
			performanceTime.setArtist(artist);
			performanceTime.setEvent(event);
			return this.timeRepository.save(performanceTime);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<PerformanceTime> findPerformanceTimeByEvent(UUID eventId, int page, int size) throws Exception {
		try {
			Pageable pagination = PageRequest.of(page, size);
			Event event = eventRepository.findById(eventId).orElseThrow(()->new Exception("Event not found"));	
			return this.timeRepository.findByEvent(event,pagination);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void deletePerformanceTimeById(Integer id) throws Exception {
		try {
			this.timeRepository.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
