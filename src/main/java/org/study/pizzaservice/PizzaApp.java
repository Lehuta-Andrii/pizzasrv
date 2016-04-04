package org.study.pizzaservice;

import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Order;
import org.study.pizzaservice.infrastructure.ApplicationContext;
import org.study.pizzaservice.infrastructure.JavaConfigApplicationContext;
import org.study.pizzaservice.repository.PizzaRepository;

public class PizzaApp {

    public static void main(String[] args) throws Exception {
	Customer customer = null;
	Order order;

	ApplicationContext ac = new JavaConfigApplicationContext();
	PizzaRepository pizzaRepository = (PizzaRepository) ac.getBean("pizzaRepository");

	System.out.println(pizzaRepository.getPizzaByID(0));
	
	
	OrderService orderService = (OrderService) ac.getBean("orderService");
	order = orderService.placeNewOrder(customer, 0, 1, 2);

	System.out.println(order);

    }

}
