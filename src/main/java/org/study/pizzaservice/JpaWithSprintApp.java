package org.study.pizzaservice;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.repository.PizzaRepository;

public class JpaWithSprintApp {

    public static void main(String[] args) {
	ConfigurableApplicationContext repAc =  new ClassPathXmlApplicationContext("repositoryContext.xml");
	//repAc.getEnvironment().setActiveProfiles("dev");
	//repAc.refresh();
	
	ConfigurableApplicationContext ac =  new ClassPathXmlApplicationContext(new String[]{"AppContext.xml"}, false);
	ac.setParent(repAc);
	ac.refresh();
	
	PizzaRepository pizzas = ac.getBean(PizzaRepository.class);
	
	Pizza pizza = new Pizza("Chilly", 50, Pizza.type.VEGETARIAN);
	
	System.out.println(pizzas.getPizzaByID(80));
	
	pizzas.create(pizza);
	
	ac.close();
	repAc.close();
	
    }

}
