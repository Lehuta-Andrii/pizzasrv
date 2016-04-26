package org.study.pizzaservice;

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
import org.study.pizzaservice.repository.AccumulativeCardRepository;
import org.study.pizzaservice.repository.CustomerRepository;
import org.study.pizzaservice.repository.PizzaRepository;
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

		// PizzaShopTemplate pizzaShop = (PizzaShopTemplate)
		// applicationContext.getBean(PizzaShopTemplate.class);
		// CustomerService customers = (CustomerService)
		// applicationContext.getBean(CustomerService.class);

		CustomerRepository customerRepository = applicationContext.getBean(CustomerRepository.class);
		AccumulativeCardRepository accRepository = applicationContext.getBean(AccumulativeCardRepository.class);
		PizzaRepository pizzaRepository = applicationContext.getBean(PizzaRepository.class);

		// addPizzas(pizzaRepository);
		// addCostumer(customerRepository);

		System.out.println(customerRepository.getCostumers());

		System.out.println(accRepository.getCards());
		accRepository.removeCustomerCard(customerRepository.getCostumerById(25L).get());
		System.out.println(accRepository.getCards());

		// AccumulativeCardService cardService = (AccumulativeCardService)
		// applicationContext
		// .getBean(AccumulativeCardService.class);
		//
		// if(cardService.setNewCard(customers.getCostumerById(0))){
		// cardService.addSumToCard(customers.getCostumerById(0), 100);
		// }
		//
		// order = pizzaShop.makeOrder(customers.getCostumerById(0), 1l, 2l, 3l,
		// 1l);
		//
		// System.out.println(order);
		// System.out.println(order.getPrice());
		//
		// System.out.println(pizzaShop.getDiscount(order));
		//
		// pizzaShop.accomplishOrder(order);
		// System.out.println(order.getState());
		//
		//
		// //System.out.println(repositoryContext.getBean(PizzaRepository.class).getPizzas());
		// repositoryContext.getBean(PizzaRepository.class).addPizza(new
		// Pizza(null, "Bavaria", 105, Pizza.Type.MEAT));
		//

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

	static void addCostumer(CustomerRepository customerRepository) {
		Customer customer = new Customer();

		List<Address> addr = new ArrayList<Address>();
		addr.add(new Address("Kiev", "Mura", "17", "6", "555322"));
		addr.add(new Address("Kiev", "Bandera", "18", "6", "555644"));
		customer.setAdresses(addr);
		customer.setName("Petro");

		customerRepository.addCustomer(customer);

	}

}
