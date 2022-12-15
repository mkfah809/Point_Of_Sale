package com.coderscampus.HotStonePOS.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coderscampus.HotStonePOS.domain.Customer;
import com.coderscampus.HotStonePOS.domain.Employee;
import com.coderscampus.HotStonePOS.domain.Order;
import com.coderscampus.HotStonePOS.domain.Pizza;
import com.coderscampus.HotStonePOS.domain.Topping;
import com.coderscampus.HotStonePOS.service.CustomerService;
import com.coderscampus.HotStonePOS.service.OrderService;
import com.coderscampus.HotStonePOS.service.PizzaService;
import com.coderscampus.HotStonePOS.service.ToppingService;

@Controller
public class orderController {
	@Autowired
	OrderService orderService;
	@Autowired
	CustomerService custService;
	@Autowired
	PizzaService pizzaService;
	@Autowired
	ToppingService toppingService;

	@GetMapping("/customer/{custId}/order")
	String saveOrder(Order order, ModelMap model, @AuthenticationPrincipal Employee emp, @PathVariable Long custId) {
		Customer customer = custService.findById(custId);
		if (customer.getCustId() != null) {
			order = orderService.save(new Order(), emp, customer, new ArrayList<>());
		}
		return "redirect:/customer/{custId}/order/" + order.getOrderId();
	}

	@GetMapping("/customer/{custId}/order/{orderId}")
	String getOrder(ModelMap model, @PathVariable Long orderId, @PathVariable Long custId) {
		Order order = orderService.findById(orderId);

		List<Pizza> pizzas = pizzaService.findAllByOrder(orderId);
		if (order.getOrderId() != null) {
			model.put("order", order);
			model.put("customer", custService.findById(custId));
			if (order.getPizzas().isEmpty()) {
				model.put("pizza", new Pizza());
			} else {
				Pizza pizza = order.getPizzas().iterator().next();
				model.put("pizza", pizza);
				model.put("toppings", toppingService.findAllToppings());
				model.put("topping", new Topping());
			}
			model.put("pizzas", pizzas);
		}

		return "order";
	}

	@PostMapping("/customer/{custId}/order/{orderId}")
	String postPizza(ModelMap model, @PathVariable Long orderId, Pizza pizza, ArrayList<Pizza> pizzas,
			ArrayList<Order> orders) {
		Pizza savedPizza = pizzaService.save(pizza);
		Order order = orderService.findById(orderId);

		if (order != null) {
			orderService.setPizzaToOrder(savedPizza, order.getPizzas(), savedPizza.getOrders(), order);
		}

		return "redirect:/customer/" + order.getCust().getCustId() + "/order/" + order.getOrderId();
	}

	@GetMapping("/customer/{custId}/order/{orderId}/pizza/{pizzaId}")
	String getPizza(ModelMap model, @PathVariable Long orderId, @PathVariable Long custId, @PathVariable Long pizzaId) {

		if (orderId != null) {
			Order order = orderService.findById(orderId);
			Pizza pizza = pizzaService.findById(pizzaId);
			model.put("pizza", pizza);
			model.put("order", order);
			model.put("customer", custService.findById(custId));
			model.put("pizzas", order.getPizzas());
			model.put("toppings", toppingService.findAllToppings());
		}

		return "order";
	}

	@PostMapping("/customer/{custId}/order/{orderId}/pizza/{pizzaId}")
	@ResponseBody
	String postTopping(@RequestBody Topping topping, ArrayList<Pizza> pizzas, ArrayList<Topping> toppings) {

		Topping foundTopping = toppingService.findByName(topping.getName());
		Pizza foundPizza = pizzaService.findById(topping.getPizzas().get(0).getPizzaId());
		Order foundOrder = foundPizza.getOrders().get(0);

		System.out.println("XXXXX order id is " + foundOrder.getOrderId());
		System.out.println("XXXXX pizza id is " + foundPizza.getPizzaId());
		System.out.println("XXXXX topping id is " + foundTopping.getId());

		pizzaService.setToppingToPizza(pizzas, toppings, foundTopping, foundPizza);
		pizzaService.save(foundPizza);

		return "redirect:/customer/{custId}/order/{orderId}/pizza/{pizzaId}";
	}

	@PostMapping("/deleteItem/from/order/{pizzaId}/{orderId}/{custId}")
	String deletePizza(@PathVariable Long pizzaId, @PathVariable Long orderId, @PathVariable Long custId,
			ModelMap model) {

		List<Pizza> pizzas = orderService.findById(orderId).getPizzas();
		Pizza pizzaRemoved = pizzas.remove(0);
		System.out.println("Deleted Item# " + pizzaRemoved.getPizzaId());
		pizzaService.saveAll(pizzas);
		return "redirect:/customer/{custId}/order/{orderId}";
	}

}
