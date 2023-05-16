package org.auca.webtech.spms.controllers;

import java.util.List;

import org.auca.webtech.spms.domain.Artist;
import org.auca.webtech.spms.payloads.GeneralResponsePayload;
import org.auca.webtech.spms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/artists")
	public ResponseEntity<GeneralResponsePayload> getAllArtists(@RequestParam(name = "page") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		try {
			List<Artist> artists = userService.findAllArtist(page, size);
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.OK.toString()).data(artists).message("Artists Found").build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.NOT_FOUND.toString()).message("Artists was not found").build(),
					HttpStatus.NOT_FOUND);
		}
	}
}
