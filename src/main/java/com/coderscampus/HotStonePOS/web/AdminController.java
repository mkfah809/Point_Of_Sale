package com.coderscampus.HotStonePOS.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coderscampus.HotStonePOS.domain.Authority;
import com.coderscampus.HotStonePOS.domain.Employee;
import com.coderscampus.HotStonePOS.domain.Topping;
import com.coderscampus.HotStonePOS.service.AdminService;
import com.coderscampus.HotStonePOS.service.AuthorityService;
import com.coderscampus.HotStonePOS.service.ToppingService;

@Controller
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	AuthorityService authService;

	@Autowired
	ToppingService toppingService;

	@GetMapping("/register/new/employee")
	public String getCreateEmployee(ModelMap model) {
		model.put("employee", new Employee());
		return "register";
	}

	@PostMapping("/register/new/employee")
	@ResponseBody
	public Boolean createEmployee(@RequestBody Employee emp, Authority auth) throws Exception {
		if (adminService.findByUsername(emp.getUsername()) == null) {
			adminService.createOrUpdateEmployee(emp);
			authService.setAuthorityToUser(emp, auth);
			return (emp == null);
		}
		return (emp != null);
	}

	@GetMapping("/dashboard/about-me/{empId}")
	public String getExistingEmployee(ModelMap model, @PathVariable Long empId) throws Exception {
		try {
			if (empId != null) {
				model.put("employee", adminService.findById(empId));
			} else {
				model.put("error", new Exception("Sorry, id is empty"));
			}
		} catch (Exception e) {
			model.put("error", new Exception("Sorry, you don't have authorization to access this page."));
		}
		return "about";
	}

	@PostMapping("/dashboard/about-me/{empId}")
	public String updateExistingEmployee(Employee emp, Authority auth) {
		System.out.println("Updating user# " + emp.getId());
		adminService.createOrUpdateEmployee(emp);
		return "redirect:/dashboard";
	}

	@GetMapping("/dashoard/employees")
	public String getListEmployees(ModelMap model) {
		model.put("employees", adminService.findAll());
		return "employees";

	}

	@PostMapping("/dashoard/employees/delete/{empId}")
	public String deleteExistingEmployee(@PathVariable Long empId) {
		System.out.println("Deleting user# " + empId);
		adminService.delete(empId);
		return "redirect:/dashboard";
	}

	@GetMapping("/dashboard/toppings")
	public String getToppingPage(Topping topping, ModelMap model) {
		model.put("topping", new Topping());
		return "addToppings";
	}

	@PostMapping("/dashboard/toppings")
	public String postTopping(Topping topping) {
		toppingService.save(topping);
		return "redirect:/dashboard";
	}
}