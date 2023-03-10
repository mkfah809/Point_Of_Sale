package com.coderscampus.HotStonePOS.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.HotStonePOS.domain.Customer;
import com.coderscampus.HotStonePOS.domain.Employee;
import com.coderscampus.HotStonePOS.domain.Order;
import com.coderscampus.HotStonePOS.domain.Pizza;
import com.coderscampus.HotStonePOS.repository.OrderRepository;
import com.coderscampus.HotStonePOS.repository.PizzaRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepo;
	@Autowired
	PizzaRepository pizzaRepo;

	@Autowired
	CustomerService custService;
	@Autowired
	empDetailsServiceImpl empService;


	public void delete(Long orderId) {
		orderRepo.deleteById(orderId);
	}

	public Order findById(Long custId) {
		return orderRepo.findById(custId).orElse(null);
	}

	public Order save(Order order, Employee emp, Customer cust, List<Order> orders) {
		if (order.getOrderId() == null) {

			order.setStatus("OPEN"); // status
			setEmployeeToOrder(order, emp, orders);
			setCustomerToOrder(order, cust, orders);
		} else {
			order.setFinalPrice(order.getFinalPrice());
			order.setStatus("CLOSE");
			order.setOrderMethod(order.getOrderMethod());
			order.setType(order.getType());
		}
		return orderRepo.save(order);
	}

	public void setEmployeeToOrder(Order order, Employee emp, List<Order> orders) {
		orders.add(order);
		order.setEmp(emp);
		emp.setOrders(orders);
	}

	public void setCustomerToOrder(Order order, Customer cust, List<Order> orders) {
		orders.add(order);
		order.setCust(cust);
		cust.setOrders(orders);

	}

	public void setPizzaToOrder(Pizza pizza, Order order) {
		List<Pizza> pizzas = order.getPizzas();
		List<Order> orders = pizza.getOrders();
		pizza.getOrders().remove(0);

		pizzas.add(pizza);
		orders.add(order);
		pizza.getOrders();
		order.getPizzas();

		pizza.setOrders(orders);
		order.setPizzas(pizzas);
	}

	public List<Order> findAll() {
		return orderRepo.findAll();
	}
	
	public void setFinalPrice(Order order, Order foundOrder, String confirmationNumber) {
		foundOrder.setConfirmationNumber(confirmationNumber);
		foundOrder.setFinalPrice(order.getFinalPrice());
		foundOrder.setOrderMethod(order.getOrderMethod());
		foundOrder.setType(order.getType());
	}


}
