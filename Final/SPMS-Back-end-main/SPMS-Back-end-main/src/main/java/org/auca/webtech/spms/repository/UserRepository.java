package org.auca.webtech.spms.repository;

import org.auca.webtech.spms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findDistinctByEmail(String email);
    Optional<User> findDistinctByUsername(String username);
	Optional<User> findUserById(UUID user);
}
