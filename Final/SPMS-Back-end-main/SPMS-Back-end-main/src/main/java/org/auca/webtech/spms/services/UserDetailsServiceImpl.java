package org.auca.webtech.spms.services;

import org.auca.webtech.spms.domain.User;
import org.auca.webtech.spms.repository.UserRepository;
import org.auca.webtech.spms.security.ApplicationSecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    	User user = userRepository.findDistinctByUsername(s).isPresent()?userRepository.findDistinctByUsername(s).get():userRepository.findDistinctByEmail(s)
                .orElseThrow(() -> new RuntimeException("Incorrect username/Email"));

        return ApplicationSecurityUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .userType(user.getType().name())
                .password(user.getPassword())
                .grantedAuthorities(user.getUserRole().grantedAuthorities())
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();

    }
}
