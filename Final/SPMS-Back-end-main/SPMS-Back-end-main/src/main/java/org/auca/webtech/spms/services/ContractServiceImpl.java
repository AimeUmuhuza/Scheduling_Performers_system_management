package org.auca.webtech.spms.services;

import java.util.List;
import java.util.UUID;

import org.auca.webtech.spms.domain.Admin;
import org.auca.webtech.spms.domain.Artist;
import org.auca.webtech.spms.domain.Contract;
import org.auca.webtech.spms.domain.FileEntity;
import org.auca.webtech.spms.domain.User;
import org.auca.webtech.spms.repository.AdminRepository;
import org.auca.webtech.spms.repository.ArtistRepository;
import org.auca.webtech.spms.repository.ContractRepository;
import org.auca.webtech.spms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service 
@Slf4j
public class ContractServiceImpl implements ContractService {
	
	@Autowired
	private ContractRepository contractRepository;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private ArtistRepository artistRepository;

	@Override
	public List<Contract> findAllContracts(int page, int size) throws Exception {
		try {
			Pageable pagination = PageRequest.of(page, size);
			return contractRepository.findAll(pagination).getContent();
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
	}

	@Override
	public void deleteContract(Integer id) throws Exception {
		try {
			 contractRepository.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
	}

	@Override
	public Contract saveContract(MultipartFile file, UUID adminId, String artistId) throws Exception {
		try {
			Admin admin = adminRepository.findById(adminId).orElseThrow(()-> new Exception("User not Found"));

			Artist artist = artistRepository.findById(UUID.fromString(artistId)).orElseThrow(()-> new Exception("Artist not Found"));

			FileEntity savedFile = fileService.save(file);
			Contract contract = Contract.builder().admin(admin).artist(artist).file(savedFile).build();
			return contractRepository.save(contract);
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
	}

}
