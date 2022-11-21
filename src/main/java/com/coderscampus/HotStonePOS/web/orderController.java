package com.coderscampus.HotStonePOS.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.HotStonePOS.domain.Customer;
import com.coderscampus.HotStonePOS.domain.Employee;
import com.coderscampus.HotStonePOS.domain.Order;
import com.coderscampus.HotStonePOS.domain.Pizza;
import com.coderscampus.HotStonePOS.service.CustomerService;
import com.coderscampus.HotStonePOS.service.Orderservice;
import com.coderscampus.HotStonePOS.service.PizzaService;

@Controller
public class orderController {
	@Autowired
	Orderservice orderService;
	@Autowired
	CustomerService custService;
	@Autowired
	PizzaService pizzaService;

	@GetMapping("/order/here/{custId}")
	public String getNewOrder(@PathVariable Long custId, @AuthenticationPrincipal Employee emp, ModelMap model) {
		if (custId != null) {
			model.put("pizza", new Pizza());
			model.put("order", new Order());
			model.put("customer", custService.findById(custId));
			model.put("employee", emp);
		}
		return "order";
	}

	@PostMapping("/order/here/{custId}")
	public String postNewOrder(ArrayList<Order> orders, Order order, @AuthenticationPrincipal Employee emp,
			Customer cust, Pizza pizza, ArrayList<Pizza> pizzas) {

		Order savedOrder = orderService.save(order, emp, cust, orders);

		return "redirect:/addItem/To/order/" + savedOrder.getOrderId() + "/" + cust.getCustId();

	}

	@GetMapping("/addItem/To/order/{orderId}/{custId}")
	public String getAddItemsToExistingOrder(@PathVariable Long custId, @PathVariable Long orderId, ModelMap model,
			Double price) {
		Order foundOrder = orderService.findById(orderId);
		price = 0.00;
		model.put("order", foundOrder);
		model.put("customer", custService.findById(custId));
		model.put("pizza", new Pizza());

		if (foundOrder != null) {
			List<Pizza> findAllByOrder = pizzaService.findAllByOrder(orderId);
			if (!findAllByOrder.isEmpty()) {
				model.put("pizzas", findAllByOrder);
				List<Double> priceForAllItems = new ArrayList<>();

				Double setPriceToItem = pizzaService.setPriceToItem(price, findAllByOrder, priceForAllItems);
				model.put("price", setPriceToItem);

				String setFinalPriceToOrder = orderService.setFinalPriceToOrder(price, priceForAllItems, foundOrder);
				model.put("finalPrice", setFinalPriceToOrder);
			}
		}
		return "order";
	}

	@PostMapping("/addItem/To/order/{orderId}/{custId}")
	public String addItemsToExistingOrder(Pizza pizza, @PathVariable Long orderId, Double price,
			@AuthenticationPrincipal Employee emp, @PathVariable Long custId, ModelMap model) {

		Pizza savedPizza = pizzaService.save(pizza);
		Order foundOrderById = orderService.findById(orderId);
		orderService.setPizzaToOrder(savedPizza, foundOrderById.getPizzas(), savedPizza.getOrders(), foundOrderById);

		if (savedPizza.getPizzaId() != null) {
			savedPizza.setPrice(pizzaService.setPriceToPizza(savedPizza, price));
			pizzaService.save(savedPizza);
		}

		model.put("price", pizzaService.setPriceToPizza(savedPizza, price));
		// orderService.save(foundOrderById, emp,
		// custService.findById(custId),savedPizza.getOrders());
		return "redirect:/addItem/To/order/{orderId}/{custId}";
	}

	@PostMapping("/deleteItem/from/order/{pizzaId}/{orderId}/{custId}")
	public String postDeleteItemsFromExistingOrder(@PathVariable Long pizzaId, @PathVariable Long orderId,
			@PathVariable Long custId, ModelMap model) {

		List<Pizza> pizzas = orderService.findById(orderId).getPizzas();
		Pizza pizzaRemoved = pizzas.remove(0);
		System.out.println("Deleted Item# " + pizzaRemoved.getPizzaId());
		pizzaService.saveAll(pizzas);

		return "redirect:/addItem/To/order/{orderId}/{custId}";
	}

}
