package org.auca.webtech.spms.services;

import java.util.List;
import java.util.UUID;

import org.auca.webtech.spms.domain.Contract;
import org.springframework.web.multipart.MultipartFile;

public interface ContractService {
	Contract saveContract(MultipartFile file, UUID admin, String artistId) throws Exception;
	List<Contract> findAllContracts(int page,int size) throws Exception;
	void deleteContract(Integer id) throws Exception;
}
