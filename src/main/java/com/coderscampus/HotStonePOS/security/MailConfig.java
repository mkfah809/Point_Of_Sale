package com.coderscampus.HotStonePOS.security;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	@Value("smtp.gmail.com")
	private String host;

	@Value("587") 
	private Integer port;
	

	MailConfig(){}
	@Bean
	public JavaMailSender mailService() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port); 
		mailSender.setUsername("minakaramm1@gmail.com");
		mailSender.setPassword("wlfymuyxnjbjbbul");
		mailSender.setJavaMailProperties(getMailProperties());
		return mailSender;
	}
	
	private Properties getMailProperties() {
		Properties prop = new Properties();
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.starttls.enable", "true");
		prop.setProperty("mail.debug", "true");
		return prop;	
	}
}
