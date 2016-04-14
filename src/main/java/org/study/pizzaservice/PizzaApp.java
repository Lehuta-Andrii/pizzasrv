package org.study.pizzaservice;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Order;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.repository.PizzaRepository;

public class PizzaApp {

    public static void main(String[] args) throws Exception {
	Customer customer = null;
	Order order;

	ConfigurableApplicationContext repAc =  new ClassPathXmlApplicationContext("RepContext.xml");
	ConfigurableApplicationContext ac =  new ClassPathXmlApplicationContext(new String[]{"AppContext.xml"}, false);
	ac.setParent(repAc);
	ac.refresh();
	
//	System.out.println(ac.getBean(Order.class));
	
	PizzaRepository pizzaRepository = (PizzaRepository) ac.getBean("pizzaRepository");

	
//	System.out.println(pizzaRepository.getPizzaByID(0));
	
	
	OrderService orderService = (OrderService) ac.getBean("orderService");
	order = orderService.placeNewOrder(customer, 0, 1, 2);

	System.out.println(order);

//	Pizza pizza = ac.getBean(Pizza.class);
//	System.out.println(pizza);
	
//	System.out.println(ac.getParent());
//	System.out.println(ac.getBean(Customer.class));
	
	repAc.close();
	ac.close();
    }

}
