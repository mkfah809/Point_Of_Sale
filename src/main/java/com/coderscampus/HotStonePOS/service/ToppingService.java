package com.coderscampus.HotStonePOS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.HotStonePOS.domain.Pizza;
import com.coderscampus.HotStonePOS.domain.Topping;
import com.coderscampus.HotStonePOS.repository.ToppingRepo;

@Service
public class ToppingService {
	@Autowired
	ToppingRepo toppingRepo;

	public Topping save(Topping topping) {
		return toppingRepo.save(topping);
	}

	public List<Topping> findAllToppings() {
		return toppingRepo.findAll();
	}

	public Topping findByName(String name) {
		return toppingRepo.findByName(name);
	}

	public Topping findById(Long toppingId) {
		return toppingRepo.findById(toppingId).orElse(null);

	}

	public Iterable<Topping> saveAll(List<Topping> toppings) {
		return toppingRepo.saveAll(toppings);
	}

	public List<Topping> findAllByPizza(Long pizzaId) {
		return toppingRepo.findAllByPizza(pizzaId);

	}

	public Double getToppingPricePerPizza(Pizza pizza) {
		ArrayList<Double> toppingPrice = new ArrayList<>();
		for (Topping topping : pizza.getToppings()) {
			toppingPrice.add(topping.getPrice());
		}
		Double totalToppingPrice = 0.0;
		for (int i = 0; i < toppingPrice.size(); i++) {

			totalToppingPrice = totalToppingPrice + (double) toppingPrice.get(i);
		}
		return totalToppingPrice;
	}

}
