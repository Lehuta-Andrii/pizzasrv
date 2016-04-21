package org.study.pizzaservice;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.study.pizzaservice.domain.Address;
import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Pizza;

public class JpaWithoutSpringApp {

    public static void main(String[] args) {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
	EntityManager em = emf.createEntityManager();

	Pizza pizza = new Pizza();
	pizza.setName("Margo");
	pizza.setPrice(132.5);
	pizza.setPizzaType(Pizza.type.MEAT);
	//pizza.setId(2);
	
	Address address = new Address(1, "df", "fdsa", "fdsa", "fdsa");

	Address address1 = new Address(2, "df2", "fdsa", "fdsa", "fdsa");
	
	
	Customer customer = new Customer();
	//customer.setId(1);
	customer.setName("john connor");
	customer.setAddresses(Arrays.asList(address, address1));
	customer.setPhones(Arrays.asList("fdsa", "dfas", "fdsa4"));

	try {
//	    Pizza res = em.find(Pizza.class, 2);
//	   System.out.println(res);
	    em.getTransaction().begin();
	    em.persist(customer);
	    em.persist(pizza);
	    em.getTransaction().commit();
	} finally {

	    em.close();
	    emf.close();
	}
    }

}
