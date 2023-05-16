package org.auca.webtech.spms.repository;

import java.util.List;
import java.util.UUID;

import org.auca.webtech.spms.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
	List<Event> findEventsByUser(UUID id);
}
