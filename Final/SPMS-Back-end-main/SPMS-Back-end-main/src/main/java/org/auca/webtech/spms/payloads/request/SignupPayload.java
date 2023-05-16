package org.auca.webtech.spms.payloads.request;

import org.auca.webtech.spms.domain.EGenre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupPayload {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String userType;
    private EGenre genre;
}
