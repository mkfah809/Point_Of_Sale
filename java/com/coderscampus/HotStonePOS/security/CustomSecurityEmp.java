package com.coderscampus.HotStonePOS.security;

import org.springframework.security.core.userdetails.UserDetails;

import com.coderscampus.HotStonePOS.domain.Employee;

public class CustomSecurityEmp extends Employee implements UserDetails {

	private static final long serialVersionUID = 1L;

	public CustomSecurityEmp() {
	} // Spring Req.

	public CustomSecurityEmp(Employee emp) {
		this.setAuthorities(emp.getAuthorities());
		this.setTitle(emp.getTitle());
		this.setId(emp.getId());
		this.setPassword(emp.getPassword());
		this.setUsername(emp.getUsername());
		
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


}
