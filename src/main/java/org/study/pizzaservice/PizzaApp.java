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

		//CustomerRepository customers = applicationContext.getBean(CustomerRepository.class);
		//AccumulativeCardRepository accrep = applicationContext.getBean(AccumulativeCardRepository.class);

		// Customer customer = new Customer();
		//
		// List<Address> addr = new ArrayList<Address>();
		// addr.add(new Address("Kiev", "Mura", "17","6", "555322"));
		// addr.add(new Address("Kiev", "Bandera", "18","6", "555644"));
		// customer.setAdresses(addr);
		//
		// customer.setName("Petro");
		//
		// AccumulativeCard aCard = new AccumulativeCardImpl();
		// aCard.setCustomer(customers.getCostumerById(12l).get());
		//
		// accrep.addCard(aCard);
		//
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
		
		PizzaRepository pizzaRepository = applicationContext.getBean(PizzaRepository.class);
		
		
		//addPizzas(pizzaRepository);
		
		
		System.out.println(pizzaRepository.getPizzas());
		
		
		Pizza somePizza = pizzaRepository.getPizzaByID(8l).get();
		somePizza.setPrice(400);
		pizzaRepository.updatePizza(somePizza);
		
		System.out.println(pizzaRepository.getPizzas());
		
		
		//pizzaRepository.deletePizza(pizzaRepository.getPizzaByID(4l).get());
		

		repositoryContext.close();
		applicationContext.close();
	}

	public static void addPizzas(PizzaRepository pizzaRepository) {
		pizzaRepository.addPizza(new Pizza("Margarita", 50, Pizza.Type.MEAT));
		pizzaRepository.addPizza(new Pizza("Americana", 76, Pizza.Type.SEA));
		pizzaRepository.addPizza(new Pizza("Chilly", 50, Pizza.Type.VEGETARIAN));
		pizzaRepository.addPizza(new Pizza("Bavaria", 105, Pizza.Type.MEAT));
	}

	static void addCostumer(CustomerRepository rep) {

	}

}
