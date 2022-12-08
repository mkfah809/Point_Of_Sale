package com.coderscampus.HotStonePOS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.coderscampus.HotStonePOS.domain.Employee;
import com.coderscampus.HotStonePOS.repository.EmployeeRepository;

@Service
public class AdminService {
	@Autowired
	EmployeeRepository empRepo;

	public Employee createOrUpdateEmployee(Employee emp) {
		emp.setPassword(new BCryptPasswordEncoder().encode(emp.getPassword()));
		if(emp.getId() != null) {
			System.out.println("emp exist");
		} else {
			System.out.println("emp doesnt exist");
		}
		return empRepo.save(emp);
	}

	public Employee findById(Long id) {
		return empRepo.findById(id).orElse(null);
	}

	public List<Employee> findAll() {
		return empRepo.findAll();
	}

	public void delete(Long id) {
		empRepo.deleteById(id);
	}

	public Employee findByUsername(String username) {
		return empRepo.findByUsername(username);

	}
}
