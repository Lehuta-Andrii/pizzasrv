package org.study.pizzaservice;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.study.pizzaservice.domain.order.Order;

public class PizzaApp {

    public static void main(String[] args) {

	Order order;

	ConfigurableApplicationContext ac = new ClassPathXmlApplicationContext("Config.xml");
	
	PizzaShopTemplate pizzaShop = (PizzaShopTemplate) ac.getBean(PizzaShopTemplate.class);
	CustomerService customers = (CustomerService) ac.getBean(CustomerService.class);
	AccumulativeCardService cardService = (AccumulativeCardService) ac.getBean(AccumulativeCardService.class);
	
	cardService.setCard(customers.getCostumerById(0)).addToCard(100);
	
	order = pizzaShop.makeOrder(customers.getCostumerById(0), 0, 1, 2, 0);

	System.out.println(order);
	System.out.println(order.getPrice());
	
	System.out.println(pizzaShop.getDiscount(order));

	
	pizzaShop.accomplishOrder(order);
	System.out.println(order.getState());

	ac.close();
    }

}
