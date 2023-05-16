package org.auca.webtech.spms.services;

import java.util.List;
import java.util.UUID;

import org.auca.webtech.spms.domain.Event;
import org.auca.webtech.spms.domain.User;
import org.auca.webtech.spms.payloads.EventPayload;
import org.auca.webtech.spms.repository.EventRepository;
import org.auca.webtech.spms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventServiceImpl  implements EventService{
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Event saveEvent(EventPayload eventPayload,UUID user) throws Exception {
		try {
			User foundUser = userRepository.findUserById(user).orElseThrow(() -> new Exception("No user found with specified username"));
			Event eventToSave = Event.builder()
					.id(UUID.randomUUID())
					.name(eventPayload.getName())
					.date(eventPayload.getDate())
					.startDate(eventPayload.getStartDate())
					.endDate(eventPayload.getEndDate())
					.location(eventPayload.getLocation())
					.user(foundUser)
					.build();
			return this.eventRepository.save(eventToSave);
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
	}

	@Override
	public Page<Event> findAddEvents(int page,int size) throws Exception {
		try {
			Pageable pagination = PageRequest.of(page, size);
			return this.eventRepository.findAll(pagination);
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
	}

	@Override
	public void deleteEvent(UUID id) throws Exception {
		try {
			 this.eventRepository.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
	}
	
	@Override
    public Event updateEvent(EventPayload eventPayload) {
        try {
            Event event = eventRepository.findById(eventPayload.getId()).orElseThrow(()-> new Error("Event not Found"));;
            event.setName(eventPayload.getName()!=null?eventPayload.getName():event.getName());
            event.setDate(eventPayload.getDate()!=null?eventPayload.getDate():event.getDate());
            event.setStartDate(eventPayload.getStartDate()!=null?eventPayload.getStartDate():event.getStartDate());
            event.setEndDate(eventPayload.getEndDate()!=null?eventPayload.getEndDate():event.getEndDate());
            event.setLocation(eventPayload.getLocation()!=null?eventPayload.getLocation():event.getLocation());
            return eventRepository.save(event);
        }catch (Exception e){
            throw  new Error(e.getMessage());
        }
    }
}
