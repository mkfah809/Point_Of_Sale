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
import org.springframework.web.bind.annotation.RequestMapping;
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
	String getToSaveOrder(ModelMap model, @AuthenticationPrincipal Employee emp, @PathVariable Long custId) {
		Customer customer = custService.findById(custId);
		if (customer.getCustId() != null) {
			Order order = orderService.save(new Order(), emp, customer, new ArrayList<>());
			return "redirect:/customer/{custId}/order/" + order.getOrderId();
		}
		return "order";
	}

	@GetMapping("/customer/{custId}/order/{orderId}")
	String getOrder(ModelMap model, @PathVariable Long orderId, @PathVariable Long custId) {
		Order order = orderService.findById(orderId);
		if (order.getOrderId() != null) {
			model.put("order", order);
			model.put("pizza", new Pizza());
			model.put("toppings", toppingService.findAllToppings());
		}
		return "order";
	}

	@PostMapping("/customer/{custId}/order/{orderId}")
	@ResponseBody
	String postOrder(@RequestBody Order order) {
		Order foundOrder = orderService.findById(order.getOrderId());
		List<Pizza> pizzas = order.getPizzas();
		Iterable<Pizza> saveAll = pizzaService.saveAll(pizzas);
		System.out.println("foundOrder");
		return "redirect:/customer/{custId}/order/{orderId}";
	}

}
