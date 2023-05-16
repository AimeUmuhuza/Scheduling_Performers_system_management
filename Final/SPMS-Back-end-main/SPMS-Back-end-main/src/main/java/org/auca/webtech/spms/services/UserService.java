package org.auca.webtech.spms.services;

import java.util.List;

import org.auca.webtech.spms.domain.Artist;
import org.auca.webtech.spms.domain.User;
import org.auca.webtech.spms.payloads.JwtAccessTokenResponsePayload;
import org.auca.webtech.spms.payloads.request.LoginPayload;
import org.auca.webtech.spms.payloads.request.SignupPayload;

public interface UserService {
    User register(SignupPayload signupPayload);
    JwtAccessTokenResponsePayload userLogin(LoginPayload loginPayload);
    List<Artist> findAllArtist(int page,int size) throws Exception;
}
