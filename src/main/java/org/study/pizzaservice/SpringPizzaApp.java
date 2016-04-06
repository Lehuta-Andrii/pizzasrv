package org.study.pizzaservice;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Order;
import org.study.pizzaservice.repository.PizzaRepository;

public class SpringPizzaApp {
    public static void main(String[] args) {
	ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("AppContext.xml");
	
	PizzaRepository pizzaRep = (PizzaRepository)appContext.getBean("pizzaRepository");
	System.out.println(pizzaRep.getPizzaByID(0));
	
	Customer customer = null;
	Order order;

	
	OrderService orderService = (OrderService) appContext.getBean("orderService");
	order = orderService.placeNewOrder(customer, 0, 1, 2);

	System.out.println(order);

	
	appContext.close();
    }
}
