package org.auca.webtech.spms.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.auca.webtech.spms.domain.Event;
import org.auca.webtech.spms.domain.PerformanceTime;
import org.auca.webtech.spms.payloads.GeneralResponsePayload;
import org.auca.webtech.spms.services.PerformanceTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/time")
public class PerformanceTimeController {

	@Autowired
	private PerformanceTimeService timeService;
	
	@PostMapping("/{artistId}/{eventId}")
	public ResponseEntity<GeneralResponsePayload> createPerformanceTime(@RequestBody PerformanceTime performanceTime,@PathVariable String artistId,@PathVariable String eventId){
		try {
			PerformanceTime savedPerformanceTime = timeService.savePerformanceTime(performanceTime, UUID.fromString(artistId),UUID.fromString(eventId));
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.CREATED.toString()).data(savedPerformanceTime).message("Time Saved Success").build(),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.BAD_REQUEST.toString()).message(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{eventId}")
	public ResponseEntity<GeneralResponsePayload> getPerformanceTimeByEvent(@RequestParam(name = "page") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,@PathVariable String eventId) {
		try {
			List<PerformanceTime> performanceTimes = timeService.findPerformanceTimeByEvent(UUID.fromString(eventId),page, size);
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.OK.toString()).data(performanceTimes).message("Performance time Found").build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.NOT_FOUND.toString()).message("Performance time was not found").build(),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{performanceTimeId}")
	public ResponseEntity<GeneralResponsePayload> deletePerformanceTime(@PathVariable Integer performanceTimeId){
		try {
			timeService.deletePerformanceTimeById(performanceTimeId);
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.CREATED.toString()).message("Time Deleted Success").build(),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.BAD_REQUEST.toString()).message(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
		}
	}
}
