package org.study.pizzaservice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.Order;

public class PizzaApp {

	public static void main(String[] args){
		Customer customer;
		AccumulativeCard accumulativeCard = null;
		Order order;

		ApplicationContext ac = new ClassPathXmlApplicationContext("Config.xml");
		OrderService orderService = (OrderService) ac.getBean("simpleOrderService");
		CustomerService costumerService = (CustomerService) ac.getBean("simpleCustomerService");
		DiscountService discountService = (DiscountService) ac.getBean("simpleDiscountService");
		AccumulativeCardService cardService = (AccumulativeCardService) ac.getBean("simpleAccumCardService");

		customer = costumerService.getCostumerById(0);
		
		if(cardService.getCard(customer) == null){
			accumulativeCard = cardService.setCard(customer);
		}
		
		order = orderService.placeNewOrder(customer, 0, 1, 2, 0);
		System.out.println(discountService.countDiscount(order, accumulativeCard));


		System.out.println(order);
		
		((ConfigurableApplicationContext)ac).close();
	}

}
