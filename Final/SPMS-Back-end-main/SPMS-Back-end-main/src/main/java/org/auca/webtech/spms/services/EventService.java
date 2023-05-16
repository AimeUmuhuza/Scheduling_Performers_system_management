package org.auca.webtech.spms.services;

import java.util.UUID;

import org.auca.webtech.spms.domain.Event;
import org.auca.webtech.spms.payloads.EventPayload;
import org.springframework.data.domain.Page;

public interface EventService {
	Event saveEvent(EventPayload eventPayload,UUID id) throws Exception;
	Event updateEvent(EventPayload eventPayload) throws Exception;
	Page<Event> findAddEvents(int page,int size) throws Exception;
	void deleteEvent(UUID uuid) throws Exception;
}