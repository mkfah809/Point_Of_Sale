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
import com.coderscampus.HotStonePOS.service.Orderservice;
import com.coderscampus.HotStonePOS.service.PizzaService;
import com.coderscampus.HotStonePOS.service.ToppingService;

@Controller
public class orderController {
	@Autowired
	Orderservice orderService;
	@Autowired
	CustomerService custService;
	@Autowired
	PizzaService pizzaService;
	@Autowired
	ToppingService toppingService;

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
	public String getAddItemsToExistingOrder(@PathVariable Long custId, @PathVariable Long orderId, ModelMap model) {
		Order foundOrder = orderService.findById(orderId);
		Double price = 0.00;
		model.put("order", foundOrder);
		model.put("customer", custService.findById(custId));
		model.put("pizza", new Pizza());

		if (foundOrder != null) {
			List<Pizza> findAllByOrder = pizzaService.findAllByOrder(orderId);
			if (!findAllByOrder.isEmpty()) {
				model.put("pizzas", findAllByOrder);
				List<Double> priceForAllItems = new ArrayList<>();
//				setPriceToItem 
				model.put("price", pizzaService.setPriceToItem(price, findAllByOrder, priceForAllItems));

				model.put("toppings", toppingService.findAllToppings());

//				setFinalPriceToOrder
				model.put("finalPrice", orderService.setFinalPriceToOrder(price, priceForAllItems, foundOrder));
			}

		}
		return "order";
	}

	@PostMapping("/addItem/To/order/{orderId}/{custId}")
	public String addItemsToExistingOrder(Pizza pizza, @PathVariable Long orderId, Double price,
			@AuthenticationPrincipal Employee emp, @PathVariable Long custId, ModelMap model, Topping topping) {

		Pizza savedPizza = pizzaService.save(pizza);
		Order foundOrderById = orderService.findById(orderId);

		if (foundOrderById != null) {
			orderService.setPizzaToOrder(savedPizza, foundOrderById.getPizzas(), savedPizza.getOrders(),
					foundOrderById);
		}

		if (savedPizza.getPizzaId() != null) { // update price to pizza
			savedPizza.setPrice(pizzaService.setPriceToPizza(savedPizza, price));
			pizzaService.save(savedPizza);
			model.put("price", pizzaService.setPriceToPizza(savedPizza, price));

		}

		// update order instead of chain to another order
		return "redirect:/addItem/To/order/{orderId}/{custId}/" + savedPizza.getPizzaId();
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

	@GetMapping("/addItem/To/order/{orderId}/{custId}/{pizzaId}")
	public String getTopping(@PathVariable Long orderId, @PathVariable Long custId, @PathVariable Long pizzaId) {
		System.out.println("find topping method controller");
		Pizza pizza = pizzaService.findById(pizzaId);
		Order order = orderService.findById(orderId);
		List<Pizza> pizzas = order.getPizzas();
		return "redirect:/addItem/To/order/{orderId}/{custId}";

	}

	@PostMapping("/addItem/To/order/{orderId}/{custId}/{pizzaId}")
	
	public String findToppingToGetPrice(@RequestBody Topping topping, @PathVariable Long pizzaId) {
		System.out.println("find topping method controller");
		Topping foundTopping = toppingService.findByName(topping.getName());
//		Pizza pizza = pizzaService.findById(pizzaId);
//		Order order = orderService.findById(orderId);
//		List<Pizza> pizzas = order.getPizzas();

		return "redirect:/addItem/To/order/{orderId}/{custId}/{pizzaId}";
	}
}
