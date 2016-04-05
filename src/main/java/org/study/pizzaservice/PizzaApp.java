package org.study.pizzaservice;

import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.repository.AccumulativeCardRepository;
import org.study.ioc.ApplicationContext;
import org.study.ioc.XMLBasedApplicationContext;
import org.study.pizzaservice.domain.customer.Customer;

public class PizzaApp {

    public static void main(String[] args) throws Exception {
	Customer customer = null;
	Order order;

	ApplicationContext ac = XMLBasedApplicationContext.getInstance().loadConfigFromXML("src/main/resources/config.xml");
	
	OrderService orderService = (OrderService)ac.getBean("simpleOrderService");
	order = orderService.placeNewOrder(customer, 0, 1, 2);
	
	AccumulativeCardRepository cardRep = (AccumulativeCardRepository)ac.getBean("cardsRepository");
	
	System.out.println(order);

    }

}
