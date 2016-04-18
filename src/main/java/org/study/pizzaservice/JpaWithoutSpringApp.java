package org.study.pizzaservice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.study.pizzaservice.domain.Pizza;

public class JpaWithoutSpringApp {

    public static void main(String[] args) {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
	EntityManager em = emf.createEntityManager();

	Pizza pizza = new Pizza();
	pizza.setName("Margo");
	pizza.setPrice(132.5);
	pizza.setPizzaType(Pizza.type.MEAT);
	pizza.setId(2);

	try {
	    Pizza res = em.find(Pizza.class, 2);
	   System.out.println(res);
//	    em.getTransaction().begin();
//	    em.persist(pizza);
//	    em.getTransaction().commit();
	} finally {

	    em.close();
	    emf.close();
	}
    }

}
