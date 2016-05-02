package org.study.pizzaservice;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Order;

@ContextConfiguration(locations = {"classpath:/AppContext.xml"})
public class SimpleOrderServiceTest extends RepositoryTestConfig {

    @Autowired
    private OrderService orderService = null;
    
    
    @Test
    public void testPlaceNewOrder() {
	System.out.println("placeNewOrder");
	Customer customer = null;
	Integer[] pizzaId = new Integer[]{0};
	Order expResult = null;
	//Order result = orderService.placeNewOrder(customer, pizzaId);
	//System.out.println(result);
//	fail("The test case is a prototype");
	
    }

}
