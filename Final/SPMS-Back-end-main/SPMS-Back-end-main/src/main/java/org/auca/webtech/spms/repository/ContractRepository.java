package org.auca.webtech.spms.repository;

import java.util.List;
import org.auca.webtech.spms.domain.Admin;
import org.auca.webtech.spms.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
	List<Contract> findEventsByAdmin(Admin admin);
}