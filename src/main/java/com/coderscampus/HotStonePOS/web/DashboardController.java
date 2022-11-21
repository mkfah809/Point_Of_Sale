package com.coderscampus.HotStonePOS.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import com.coderscampus.HotStonePOS.domain.Employee;
import com.coderscampus.HotStonePOS.service.AdminService;

@Controller
public class DashboardController {
	@Autowired
	AdminService adminService;

	@GetMapping("/dashboard")
	public String getDashboard(@AuthenticationPrincipal Employee emp, ModelMap model) throws Exception {
		Employee foundById = adminService.findById(emp.getId());
		try {
			model.put("employee", foundById);
			model.put("access", emp.getAuthorities().iterator().next());

		} catch (Exception e) {

			model.put("error", new Exception("You don't have a role at this point"));
			throw new Exception("You are not an admin to access this page");
		}
		return "dashboard";
	}

}
