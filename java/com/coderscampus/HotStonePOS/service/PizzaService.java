package com.coderscampus.HotStonePOS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.HotStonePOS.domain.Pizza;
import com.coderscampus.HotStonePOS.domain.Topping;
import com.coderscampus.HotStonePOS.repository.PizzaRepository;

@Service
public class PizzaService {

	@Autowired
	PizzaRepository pizzaRepo;

	public Pizza save(Pizza pizza) {
		if (pizza.getPizzaId() != null) {

		} else {
			

		}
		return pizzaRepo.save(pizza);

	}

	public void delete(Long pizzaId) {
		pizzaRepo.deleteById(pizzaId);
	}

	public List<Pizza> findAll() {
		return pizzaRepo.findAll();
	}

	public Pizza findById(Long id) {
		return pizzaRepo.findById(id).orElse(null);
	}

	public List<Pizza> findAllByOrder(Long orderId) {
		return pizzaRepo.findAllByOrder(orderId);

	}


	public Iterable<Pizza> saveAll(List<Pizza> pizzas) {
		return pizzaRepo.saveAll(pizzas);
	}


}
