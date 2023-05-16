package org.auca.webtech.spms.payloads;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtAccessTokenResponsePayload {
	private String message;
    private String status;
    private String authenticationToken;
    private UUID userId;
    private String username;
    private String email;
    private String userType;
    private String fullName;
    private Collection<? extends GrantedAuthority> authorities;
}
