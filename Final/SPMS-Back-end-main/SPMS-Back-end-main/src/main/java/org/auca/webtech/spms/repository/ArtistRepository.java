package org.auca.webtech.spms.repository;

import java.util.UUID;

import org.auca.webtech.spms.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, UUID> {
}