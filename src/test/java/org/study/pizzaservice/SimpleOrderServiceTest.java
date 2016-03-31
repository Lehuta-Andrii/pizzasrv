package org.study.pizzaservice;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mock;
import org.study.pizzaservice.domain.customer.Customer;

public class SimpleOrderServiceTest {

    @Mock
    private Customer mockCustomer;

    private SimpleOrderService orderService;

    @Test(expected = TooManyPizzas.class)
    public void placeNewOrderTestOnEmptyListOfPizzas() {
	orderService = new SimpleOrderService();

	orderService.placeNewOrder(mockCustomer);
    }

    @Test(expected = TooManyPizzas.class)
    public void placeNewOrderTestOnMoreThanTenPizzas() {
	orderService = new SimpleOrderService();

	orderService.placeNewOrder(mockCustomer, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
    }
    
    @Test
    public void placeNewOrderTestOnMoreThanZeroLessThanTenPizzas() {
	orderService = new SimpleOrderService();

	assertTrue(orderService.placeNewOrder(mockCustomer, 1, 1, 1, 1) != null);
    }
    

}
