package com.coderscampus.HotStonePOS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.HotStonePOS.domain.Topping;
import com.coderscampus.HotStonePOS.repository.ToppingRepo;

@Service
public class ToppingService {
	@Autowired
	ToppingRepo toppingRepo;

	 public Topping save(Topping topping) {
		return toppingRepo.save(topping);
		
	}



}
