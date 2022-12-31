package com.coderscampus.HotStonePOS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coderscampus.HotStonePOS.domain.Employee;
import com.coderscampus.HotStonePOS.repository.EmployeeRepository;
import com.coderscampus.HotStonePOS.security.CustomSecurityEmp;

@Service
public class empDetailsServiceImpl implements UserDetailsService {

	@Autowired
	EmployeeRepository empRepo;

	@Override
	public UserDetails loadUserByUsername(String empUsername) throws UsernameNotFoundException {
		Employee emp = empRepo.findByUsername(empUsername);
//		System.out.println("LOGGEDIN USER: " + emp.getUsername());
//		System.out.println(emp.getPassword());
		
		if (emp == null)
			throw new UsernameNotFoundException("Username or password was incorrect");
		
		return new CustomSecurityEmp(emp); // map emp => empDetail
	}


}
