package com.coderscampus.HotStonePOS.service;

import java.util.Optional;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.coderscampus.HotStonePOS.domain.Address;
import com.coderscampus.HotStonePOS.domain.Customer;
import com.coderscampus.HotStonePOS.domain.Order;
import com.coderscampus.HotStonePOS.repository.AddressRepository;
import com.coderscampus.HotStonePOS.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	CustomerRepository custRepo;

	@Autowired
	AddressRepository addRepo;

	public Customer findByPhone(String phone) {
		return custRepo.findByPhone(phone);
	}

	public Customer findById(Long id) {
		Optional<Customer> optional = custRepo.findById(id);
		Customer cust = null;
		if (optional.isPresent())
			cust = optional.get();
		else
			throw new RuntimeException("cust not found for id : " + id);
		return cust;

	}

	public Customer save(Customer cust) {
		return custRepo.save(cust);
	}

	public Address setAddressToCustomer(Customer cust, Address address) {
		address.setAddressLine1(cust.getAddress().getAddressLine1());
		address.setAddressLine2(cust.getAddress().getAddressLine2());
		address.setCity(cust.getAddress().getCity());
		address.setRegion(cust.getAddress().getRegion());
		address.setCountry(cust.getAddress().getCountry());
		address.setZipCode(cust.getAddress().getZipCode());
		address.setCustId(cust.getCustId());
		address.setCust(cust);
		cust.setAddress(address);
		return addRepo.save(address);
	}

	public String getConfirmationNumber() {
		String confirmationNumber =  new Random().ints(48, 123)
				.filter(num -> (num < 58 || num > 64) && (num < 91 || num > 96))
				.limit(15)
				.mapToObj(c -> (char) c).collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
				.toString();
		return confirmationNumber;

	}

	public void sendMail(Customer customer, Order order) {
		final String emailToRecipient = customer.getEmail();
		 final String emailSubject = "HotStone Pizzeria";
		final String emailMessage = "Thank you for Choosing HotStone Restaurant\n"
				+ "Your paying in " + order.getOrderMethod()
				+ "\nYour Total is: " + order.getFinalPrice()
				+ "\nYour confirmation# is: " + order.getConfirmationNumber();
		mailSender.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelper.setTo(emailToRecipient);
				mimeMessageHelper.setText(emailMessage, true);
				mimeMessageHelper.setSubject(emailSubject);
			}
		});

	}

}
