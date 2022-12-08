package com.coderscampus.HotStonePOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderscampus.HotStonePOS.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Customer findByPhone(String phone);
}
