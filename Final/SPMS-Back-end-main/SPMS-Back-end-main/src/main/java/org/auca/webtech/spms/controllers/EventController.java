package org.auca.webtech.spms.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.auca.webtech.spms.domain.Event;
import org.auca.webtech.spms.payloads.EventPayload;
import org.auca.webtech.spms.payloads.GeneralResponsePayload;
import org.auca.webtech.spms.security.ApplicationSecurityUser;
import org.auca.webtech.spms.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/event")
@Slf4j
public class EventController {

	@Autowired
	private EventService eventService;

	@PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<GeneralResponsePayload> createHouse(@RequestBody @Valid EventPayload eventPayLoad,
			@AuthenticationPrincipal ApplicationSecurityUser user) {
		try {
			Event savedEvent = eventService.saveEvent(eventPayLoad, user.getId());
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.CREATED.toString()).data(savedEvent).message("Event Saved Success").build(),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.NOT_FOUND.toString()).message(e.getMessage()).build(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<GeneralResponsePayload> getAllEvents(@RequestParam(name = "page") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		try {
			Page<Event> events = eventService.findAddEvents(page, size);
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.OK.toString()).data(events).message("Events Found").build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.NOT_FOUND.toString()).message("Events was not found").build(),
					HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<GeneralResponsePayload> updateEvent(@RequestBody @Valid EventPayload event,
			@AuthenticationPrincipal ApplicationSecurityUser user) {
		try {
			Event updatedEvent = eventService.updateEvent(event);
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.OK.toString()).data(updatedEvent).message("Event updated success").build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.NOT_MODIFIED.toString()).message(e.getMessage()).build(),
					HttpStatus.NOT_MODIFIED);
		}
	}

	@DeleteMapping("/{eventId}")
	public ResponseEntity<GeneralResponsePayload> deleteEvent(@PathVariable String eventId) {
		try {
			eventService.deleteEvent(UUID.fromString(eventId));
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.OK.toString()).message("Event Deleted Success").build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.NOT_FOUND.toString()).message(e.getMessage()).build(), HttpStatus.NOT_FOUND);
		}
	}

}
