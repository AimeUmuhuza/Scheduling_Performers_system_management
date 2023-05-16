package org.auca.webtech.spms.controllers;

import lombok.extern.slf4j.Slf4j;

import org.auca.webtech.spms.domain.User;
import org.auca.webtech.spms.payloads.GeneralResponsePayload;
import org.auca.webtech.spms.payloads.JwtAccessTokenResponsePayload;
import org.auca.webtech.spms.payloads.request.LoginPayload;
import org.auca.webtech.spms.payloads.request.SignupPayload;
import org.auca.webtech.spms.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<GeneralResponsePayload> registerUser(@RequestBody @Valid SignupPayload signupPayload){
        User user = userService.register(signupPayload);
        log.info("user "+signupPayload+" is ");
        return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
            .statusCode(HttpStatus.CREATED.toString())
                .data(user)
                .message("Your registration was completed successfully").build(), HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginPayload loginPayload){
    	try {
            log.info("User "+loginPayload.getUsername()+"  is Logging in");
            JwtAccessTokenResponsePayload loginResponse = userService.userLogin(loginPayload);
            return new ResponseEntity<JwtAccessTokenResponsePayload>(loginResponse, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.toString())
                    .message(e.getMessage()).build(), HttpStatus.BAD_REQUEST
            );
        }
    }
}
