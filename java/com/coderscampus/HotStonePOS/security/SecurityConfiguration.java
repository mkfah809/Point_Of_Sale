package com.coderscampus.HotStonePOS.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration()
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encode = passwordEncoder.encode("00");
		System.out.println("Paste this in the Db: " + encode);
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeRequests()
		.antMatchers("/about").hasAnyRole("ADMIN","USER")
		.antMatchers("/addToppings").hasAnyRole("ADMIN")
		.antMatchers("/customer").hasAnyRole("ADMIN","USER")
		.antMatchers("dashboard").hasAnyRole("ADMIN","USER")
		.antMatchers("/Employees").hasAnyRole("ADMIN")
		.antMatchers("/order").hasAnyRole("ADMIN","USER")
		.antMatchers("/register").hasAnyRole("ADMIN")
		.antMatchers("/searchCustomer").hasAnyRole("ADMIN","USER")
		.and()
		.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/dashboard")
		.permitAll();
	
		
	
		}

}
