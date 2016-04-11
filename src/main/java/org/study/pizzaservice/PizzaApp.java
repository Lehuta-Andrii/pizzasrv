package org.study.pizzaservice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.Order;

public class PizzaApp {

	public static void main(String[] args) {
		Customer customer;
		AccumulativeCard accumulativeCard = null;
		Order order;

		ConfigurableApplicationContext ac = new ClassPathXmlApplicationContext("Config.xml");
		PizzaShopTemplate pizzaShop = (PizzaShopTemplate) ac.getBean(PizzaShopTemplate.class);
		
		order = pizzaShop.makeOrder(null, 0, 1, 2, 0);
		ac.getBean(AccumulativeCardService.class);
		System.out.println(order);
		System.out.println(pizzaShop.getDiscount(order));

		ac.close();
	}

}
