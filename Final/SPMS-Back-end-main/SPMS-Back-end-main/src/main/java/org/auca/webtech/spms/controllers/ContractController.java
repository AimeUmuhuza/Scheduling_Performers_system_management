package org.auca.webtech.spms.controllers;

import java.util.List;

import org.auca.webtech.spms.domain.Contract;
import org.auca.webtech.spms.domain.Event;
import org.auca.webtech.spms.payloads.GeneralResponsePayload;
import org.auca.webtech.spms.security.ApplicationSecurityUser;
import org.auca.webtech.spms.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/contract")
public class ContractController {

	@Autowired
	private ContractService contractService;
	@PostMapping("/{artistId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GeneralResponsePayload> upload(@RequestParam("file") MultipartFile file,@AuthenticationPrincipal ApplicationSecurityUser user,@PathVariable String artistId) {
        try {
        	Contract contract= contractService.saveContract(file,user.getId(),artistId);

        	return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.CREATED.toString()).data(contract).message("Contract Saved Success").build(),
					HttpStatus.CREATED);
        } catch (Exception e) {
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.BAD_REQUEST.toString()).message(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
		}
    }
	
	@GetMapping
	public ResponseEntity<GeneralResponsePayload> getAllContracts(@RequestParam(name = "page") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		try {
			List<Contract> contracts = contractService.findAllContracts(page, size);
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.OK.toString()).data(contracts).message("Contracts Found").build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
					.statusCode(HttpStatus.NOT_FOUND.toString()).message("Contract was not found").build(),
					HttpStatus.NOT_FOUND);
		}
	}
}
