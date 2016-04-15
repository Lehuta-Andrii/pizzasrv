package org.study.pizzaservice;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.service.AccumulativeCardService;
import org.study.pizzaservice.service.CustomerService;
import org.study.pizzaservice.service.PizzaShopTemplate;

public class PizzaApp {

    public static void main(String[] args) {

	Order order;

	ConfigurableApplicationContext repositoryContext = new ClassPathXmlApplicationContext("RepositoryContext.xml");
	ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext(
		new String[] { "ApplicationContext.xml" }, false);

	applicationContext.setParent(repositoryContext);
	applicationContext.refresh();

	PizzaShopTemplate pizzaShop = (PizzaShopTemplate) applicationContext.getBean(PizzaShopTemplate.class);
	CustomerService customers = (CustomerService) applicationContext.getBean(CustomerService.class);
	AccumulativeCardService cardService = (AccumulativeCardService) applicationContext
		.getBean(AccumulativeCardService.class);

	if(cardService.setNewCard(customers.getCostumerById(0))){
	    cardService.addSumToCard(customers.getCostumerById(0), 100);
	}

	order = pizzaShop.makeOrder(customers.getCostumerById(0), 1, 2, 3, 1);

	System.out.println(order);
	System.out.println(order.getPrice());

	System.out.println(pizzaShop.getDiscount(order));

	pizzaShop.accomplishOrder(order);
	System.out.println(order.getState());

	repositoryContext.close();
	applicationContext.close();
    }

}
