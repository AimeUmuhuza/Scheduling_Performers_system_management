package org.auca.webtech.spms.repository;

import java.util.UUID;

import org.auca.webtech.spms.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
}