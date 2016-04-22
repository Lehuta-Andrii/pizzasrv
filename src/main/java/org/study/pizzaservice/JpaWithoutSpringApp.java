package org.study.pizzaservice;

import java.util.Arrays;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.study.pizzaservice.domain.Address;
import org.study.pizzaservice.domain.CheckCustomer;
import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.RegistratedCustomer;
import org.study.pizzaservice.domain.State;

public class JpaWithoutSpringApp {

    public static void main(String[] args) {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
	EntityManager em = emf.createEntityManager();

	Pizza pizza = new Pizza();
	pizza.setName("Margo");
	pizza.setPrice(132.5);
	pizza.setPizzaType(Pizza.type.MEAT);
	//pizza.setId(2);
	
	Address address = new Address("fads", "fdsa23", "4r4", "324");
	address.setState(new State("active"));

	Address address1 = new Address();
	address.setState(new State("active"));
	
	
	Customer customer = new Customer();
	customer.setName("john connor");
	customer.setAddress(Arrays.asList(address, address1));
	customer.setPhones(Arrays.asList("fdsa", "dfas", "fdsa4"));

	
	
	address.setCustomer(customer);
	address1.setCustomer(customer);
	try {
	    em.getTransaction().begin();
	    em.persist(customer);
	    em.flush();
	    em.getTransaction().commit();
	    em.clear();
	    
	} finally {

	    em.close();
	    emf.close();
	}
    }

}
