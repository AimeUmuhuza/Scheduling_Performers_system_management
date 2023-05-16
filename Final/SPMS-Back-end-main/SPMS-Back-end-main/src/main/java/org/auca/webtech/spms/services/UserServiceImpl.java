package org.auca.webtech.spms.services;

import lombok.extern.slf4j.Slf4j;

import org.auca.webtech.spms.domain.Admin;
import org.auca.webtech.spms.domain.Artist;
import org.auca.webtech.spms.domain.EUserType;
import org.auca.webtech.spms.domain.User;
import org.auca.webtech.spms.exceptions.UserWithProvidedEmailExistException;
import org.auca.webtech.spms.payloads.JwtAccessTokenResponsePayload;
import org.auca.webtech.spms.payloads.request.LoginPayload;
import org.auca.webtech.spms.payloads.request.SignupPayload;
import org.auca.webtech.spms.repository.ArtistRepository;
import org.auca.webtech.spms.repository.UserRepository;
import org.auca.webtech.spms.security.ApplicationSecurityRoles;
import org.auca.webtech.spms.security.ApplicationSecurityUser;
import org.auca.webtech.spms.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository ownerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils utils;
    private final PasswordEncoder encoder;
    
    @Autowired
    private ArtistRepository artistRepository;

    public UserServiceImpl(UserRepository ownerRepository, AuthenticationManager authenticationManager, JwtUtils utils, PasswordEncoder encoder) {
        this.ownerRepository = ownerRepository;
        this.authenticationManager = authenticationManager;
        this.utils = utils;
        this.encoder = encoder;
    }

    @Override
    public User register(SignupPayload signupPayload) {
        Optional<User> existingUser = ownerRepository.findDistinctByEmail(signupPayload.getEmail());
        if(existingUser.isPresent()){
            log.error("Attempting to create duplicate user with email: {}", existingUser.get().getEmail());
            throw new UserWithProvidedEmailExistException("Provided email is already in use.Please provide a valid email");
        }
        User toBeRegistered = null;
        try {
        	if(signupPayload.getUserType()!=null && EUserType.ARTIST.name().equals(signupPayload.getUserType())) {
        		toBeRegistered = Artist.builder()
                        .id(UUID.randomUUID())
                        .firstName(signupPayload.getFirstName())
                        .lastName(signupPayload.getLastName())
                        .username(signupPayload.getUsername())
                        .email(signupPayload.getEmail())
                        .password(encoder.encode(signupPayload.getPassword()))
                        .genre(signupPayload.getGenre())
                        .type(EUserType.ARTIST)
                        .userRole(ApplicationSecurityRoles.ARTIST)
                        .isVerified(true)
                        .build();
        	}else {
            toBeRegistered = Admin.builder()
                    .id(UUID.randomUUID())
                    .firstName(signupPayload.getFirstName())
                    .lastName(signupPayload.getLastName())
                    .username(signupPayload.getUsername())
                    .email(signupPayload.getEmail())
                    .password(encoder.encode(signupPayload.getPassword()))
                    .type(EUserType.ADMIN)
                    .isVerified(true)
                    .build();
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ownerRepository.save(toBeRegistered);
    }

    public JwtAccessTokenResponsePayload userLogin(LoginPayload loginPayload) {
    	Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginPayload.getUsername(), loginPayload.getPassword())
        );
        ApplicationSecurityUser user = (ApplicationSecurityUser) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = utils.generateJwtToken(authentication);
        return JwtAccessTokenResponsePayload.builder()
                .status(HttpStatus.OK.toString())
                .message("Successfully Logged in.Welcome to your account")
                .authenticationToken(jwt)
                .username(user.getUsername())
                .userId(user.getId())
                .email(user.getEmail())
                .userType(user.getUserType())
                .fullName(user.getFullName())
                .authorities(user.getAuthorities()).build();
    }

	@Override
	public List<Artist> findAllArtist(int page, int size) throws Exception {
		try {
			Pageable pagination = PageRequest.of(page, size);
			return this.artistRepository.findAll(pagination).getContent();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
