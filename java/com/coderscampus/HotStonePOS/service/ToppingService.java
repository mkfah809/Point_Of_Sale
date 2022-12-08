package com.coderscampus.HotStonePOS.service;

import java.util.List;

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
	 
	 public List<Topping> findAllToppings(){
		 return toppingRepo.findAll();
	 }

	 public Topping findByName(String name) {
		 return toppingRepo.findByName(name);
	 }

	public Topping findById(Long toppingId) {
		return toppingRepo.findById(toppingId).orElse(null);
		
	}


}
