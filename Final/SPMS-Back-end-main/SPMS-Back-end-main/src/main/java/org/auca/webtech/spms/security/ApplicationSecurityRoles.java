package org.auca.webtech.spms.security;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.auca.webtech.spms.security.ApplicationSecurityPermissions.*;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum ApplicationSecurityRoles {
	ADMIN(Sets.newHashSet(VIEW_USER, READ_USER,ACCESS_EVENT)),
	ARTIST(Sets.newHashSet(VIEW_TIME));

    private Set<ApplicationSecurityPermissions> permissions;

    public Set<SimpleGrantedAuthority> grantedAuthorities(){
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }

}
