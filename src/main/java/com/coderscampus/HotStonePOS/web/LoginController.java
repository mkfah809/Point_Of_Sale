package com.coderscampus.HotStonePOS.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class LoginController {


	@RequestMapping("/")
	public String getRedirectWelcomePage() {
		System.out.println(" redirect login page");

		return "redirect:/login";
	}
	
	
	@GetMapping("/login")
	public String login() {
		System.out.println("login page");
		return "login";
	}

}
