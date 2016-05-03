package org.study.pizzaservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCardImpl;
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.domain.order.OrderState;
import org.study.pizzaservice.domain.order.state.CanceledState;
import org.study.pizzaservice.repository.AccumulativeCardRepository;
import org.study.pizzaservice.repository.CustomerRepository;
import org.study.pizzaservice.repository.OrderRepository;
import org.study.pizzaservice.repository.PizzaRepository;
import org.study.pizzaservice.service.AccumulativeCardService;
import org.study.pizzaservice.service.CustomerService;
import org.study.pizzaservice.service.PizzaShopTemplate;

public class PizzaApp {

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		Order order;

		ConfigurableApplicationContext repositoryContext = new ClassPathXmlApplicationContext("RepositoryContext.xml");
		ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				new String[] { "ApplicationContext.xml" }, false);

		applicationContext.setParent(repositoryContext);
		applicationContext.refresh();
		
//		addPizzas(applicationContext.getBean(PizzaRepository.class));
//		addCustomer(applicationContext.getBean(CustomerRepository.class));
		
//
//		PizzaShopTemplate pizzaShop = (PizzaShopTemplate) applicationContext.getBean(PizzaShopTemplate.class);
//		CustomerService customers = (CustomerService) applicationContext.getBean(CustomerService.class);
//
//		AccumulativeCardService cardService = (AccumulativeCardService) applicationContext
//				.getBean(AccumulativeCardService.class);
//
//		if (cardService.setNewCard(customers.getCostumerById(24l))) {
//			cardService.addSumToCard(customers.getCostumerById(24l), 100);
//		}
//
//		order = pizzaShop.makeOrder(customers.getCostumerById(24l), 20l, 21l, 22l, 23l);
//
//		System.out.println(order);
//		System.out.println(order.getPrice());
//
//		System.out.println(pizzaShop.getDiscount(order));

//		pizzaShop.accomplishOrder(order);
//		System.out.println(order.getState());

		PizzaRepository pizzas = applicationContext.getBean(PizzaRepository.class);
		Pizza pizza  = pizzas.getPizzaByID(18l).get();
		pizzas.deletePizza(pizza);
		
		
		
		repositoryContext.close();
		applicationContext.close();
	}

	private static void addAccumulativeCard(Customer customer, AccumulativeCardRepository accRepository) {
		AccumulativeCard aCard = new AccumulativeCardImpl();
		aCard.setCustomer(customer);

		accRepository.addCard(aCard);
	}

	public static void addPizzas(PizzaRepository pizzaRepository) {
		pizzaRepository.addPizza(new Pizza("Margarita", 50, Pizza.Type.MEAT));
		pizzaRepository.addPizza(new Pizza("Americana", 76, Pizza.Type.SEA));
		pizzaRepository.addPizza(new Pizza("Chilly", 50, Pizza.Type.VEGETARIAN));
		pizzaRepository.addPizza(new Pizza("Bavaria", 105, Pizza.Type.MEAT));
	}

	static Customer addCustomer(CustomerRepository customerRepository) {
		Customer customer = new Customer();

		List<Address> addr = new ArrayList<Address>();
		addr.add(new Address("Kiev", "Mura", "17", "6", "555322"));
		addr.add(new Address("Kiev", "Bandera", "18", "6", "555644"));
		customer.setAdresses(addr);
		customer.setName("Petro");

		customerRepository.addCustomer(customer);
		return customer;

	}

}
