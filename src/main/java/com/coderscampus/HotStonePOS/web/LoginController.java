package com.coderscampus.HotStonePOS.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coderscampus.HotStonePOS.domain.Authority;
import com.coderscampus.HotStonePOS.domain.Employee;
import com.coderscampus.HotStonePOS.service.AdminService;
import com.coderscampus.HotStonePOS.service.AuthorityService;

@Controller
public class LoginController {
	@Autowired
	AdminService adminService;
	@Autowired
	AuthorityService authService;

	@RequestMapping("/")
	public String getRedirectWelcomePage() {
		System.out.println(" redirect login page");
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login(ModelMap model, Employee emp) {
		List<Employee> emps = adminService.findAll();
		if (!emps.isEmpty()) {
			for (Employee employee : emps) {
				model.put("employee", employee);
			}
		}
		System.out.println("login page");
		return "login";
	}

	@GetMapping("/super-admin-registeration-for-first-time")
	public String getSuperAdminRegisteration(ModelMap model) {
		model.put("employee", new Employee());
		return "registerSA";
	}

	@PostMapping("/super-admin-registeration-for-first-time")
	public String postFirstEmployeeAsAdmin(Employee emp, Authority auth) {
		emp.setTitle("Manager");
		Employee savedEmp = adminService.createOrUpdateEmployee(emp);
		Authority setAuthorityToUser = authService.setAuthorityToUser(savedEmp, auth);
		System.out.println("savedEmp for first time: " + savedEmp.getId());
		System.out.println("savedAuthority  for first time: " + setAuthorityToUser.getAuthority());

		return "redirect:/login";
	}

}
