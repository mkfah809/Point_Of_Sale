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
		model.put("customer", customer);
		Order savedOrder = orderService.save(order, emp, customer, new ArrayList<>());

		model.put("order", savedOrder);
		model.put("pizza", new Pizza());
		return "redirect:/customer/{custId}/order/" + savedOrder.getOrderId();
	}

	@GetMapping("/customer/{custId}/order/{orderId}")
	String getOrder(ModelMap model, @PathVariable Long orderId, @PathVariable Long custId, Pizza pizza) {
		Order order = orderService.findById(orderId);
		List<Pizza> pizzas = order.getPizzas();
		if (!pizzas.isEmpty()) {
			pizza = pizzas.get(pizzas.size() - 1);
		}

		if (order.getOrderId() != null) {
			model.put("customer", order.getCust());
			model.put("order", order);
			model.put("pizza", new Pizza());
			model.put("pizzas", pizzas);
			model.put("toppings", toppingService.findAllToppings());
		}

		if (!pizza.getToppings().isEmpty()) {
			Double finalPizzaPrice = pizza.getPrice() + (toppingService.getToppingPricePerPizza(pizza) * pizza.getQty());
			pizza.setPrice(finalPizzaPrice);
		}

		return "order";
	}

	@PostMapping("/post-pizza/customer/{custId}/order/{orderId}")
	String postPizza(@RequestBody Pizza pizza) {

		Order order = orderService.findById(pizza.getOrders().get(0).getOrderId());
		if (!pizza.getToppings().isEmpty()) {
			for (String topping : pizza.getToppings().get(0).toString().split(",")) {
				Topping toppingFound = toppingService.findByName(topping);
				pizza.getToppings().add(toppingFound);
			}
			// to make sure clearing the topping array we inserted the first time
			pizza.getToppings().remove(0);

		}
		List<Pizza> pizzas = order.getPizzas();
		List<Order> orders = pizza.getOrders();

		pizza.getOrders().remove(0);
		pizzas.add(pizza);
		orders.add(order);
		pizza.setOrders(orders);
		order.setPizzas(pizzas);
		pizzaService.save(pizza);

		return "redirect:/customer/" + order.getCust().getCustId() + "/order/" + order.getOrderId();

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

	@PostMapping("/order-price/customer/{custId}/order/{orderId}")
	String postFinalPrice(@RequestBody Order order) {
		Order foundOrder = orderService.findById(order.getOrderId());
		orderService.save(foundOrder, foundOrder.getEmp(), foundOrder.getCust(), new ArrayList<Order>());
		return "redirect:/customer/" + foundOrder.getCust().getCustId() + "/order/" + foundOrder.getOrderId();
	}

}
