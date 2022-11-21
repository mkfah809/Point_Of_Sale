package com.coderscampus.HotStonePOS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.HotStonePOS.domain.Authority;
import com.coderscampus.HotStonePOS.domain.Employee;
import com.coderscampus.HotStonePOS.repository.AuthorityRepository;

@Service
public class AuthorityService {

	@Autowired
	AuthorityRepository authRepo;

	public Authority setAuthorityToUser(Employee emp, Authority auth) {

		if (emp.getTitle().equalsIgnoreCase("Manager")) {
			auth.setAuthority("ROLE_ADMIN");
		} else {
			auth.setAuthority("ROLE_USER");
		}
		auth.setEmp(emp);
		return authRepo.save(auth);
	}
}
