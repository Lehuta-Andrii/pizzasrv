package org.study.pizzaservice;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Order;
import org.study.pizzaservice.domain.Pizza;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest {

    @Mock
    private Customer mockCustomer;

    private Order order;

    @Test
    public void getPriceTestOnEmptyPizzaList() {
	when(mockCustomer.hasAccumulativeCard()).thenReturn(false);

	List<Pizza> emptyPizzasList = new ArrayList<Pizza>();
	order = new Order(mockCustomer, emptyPizzasList);

	assertTrue(Double.compare(0, order.getPrice()) == 0);
    }

    @Test
    public void countPriceTestOnPizzaListWithoutAnyDiscount() {
	when(mockCustomer.hasAccumulativeCard()).thenReturn(false);

	double expectedPrice = 135;
	List<Pizza> pizzasList = new ArrayList<Pizza>();

	pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
	pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
	pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

	order = new Order(mockCustomer, pizzasList);
	assertTrue(Double.compare(order.getPrice(), expectedPrice) == 0);
    }

    @Test
    public void countDiscountTestOnPizzaListWithoutAccumulativeDiscountAndMinimumPizzas() {
	when(mockCustomer.hasAccumulativeCard()).thenReturn(false);

	double expectedDiscount = 0;
	List<Pizza> pizzasList = new ArrayList<Pizza>();

	pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
	pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
	pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

	order = new Order(mockCustomer, pizzasList);
	assertTrue(Double.compare(order.getDiscount(), expectedDiscount) == 0);
    }

    @Test
    public void countDiscountTestOnPizzaListWithFourPizzasAndWithoutAccumulativeDiscount() {
	when(mockCustomer.hasAccumulativeCard()).thenReturn(false);

	double priceOfMostExpensivePizza = 100;
	double expectedDiscount = priceOfMostExpensivePizza * 0.3;
	List<Pizza> pizzasList = new ArrayList<Pizza>();

	pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
	pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
	pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
	pizzasList.add(new Pizza("Expensive Pizza", priceOfMostExpensivePizza, Pizza.type.MEAT));

	order = new Order(mockCustomer, pizzasList);
	assertTrue(Double.compare(order.getDiscount(), expectedDiscount) == 0);
    }

    @Test
    public void countDiscountTestOnPizzaListWithAccumulativeDiscountWhenDiscountLessThanThirtyPercent() {

	double sumOnDiscountCard = 40;

	when(mockCustomer.hasAccumulativeCard()).thenReturn(true);
	when(mockCustomer.getSumFromAccummulative()).thenReturn(sumOnDiscountCard);

	double expectedDiscount = 0.1 * sumOnDiscountCard;
	List<Pizza> pizzasList = new ArrayList<Pizza>();

	pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
	pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
	pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

	order = new Order(mockCustomer, pizzasList);
	assertTrue(Double.compare(order.getDiscount(), expectedDiscount) == 0);
    }

    @Test
    public void countDiscountTestOnPizzaListWithAccumulativeDiscountWhenDiscountMoreThanThirtyPercent() {

	double sumOnDiscountCard = 406;

	when(mockCustomer.hasAccumulativeCard()).thenReturn(true);
	when(mockCustomer.getSumFromAccummulative()).thenReturn(sumOnDiscountCard);

	double expectedPrice = 135;
	double expectedDiscount = 0.3 * expectedPrice;
	List<Pizza> pizzasList = new ArrayList<Pizza>();

	pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
	pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
	pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

	order = new Order(mockCustomer, pizzasList);
	assertTrue(Double.compare(order.getDiscount(), expectedDiscount) == 0);
    }

    @Test
    public void countDiscountTestOnFourPizzasListWithAccumulativeDiscountWhenDiscountMoreThanThirtyPercent() {

	double sumOnDiscountCard = 700;

	when(mockCustomer.hasAccumulativeCard()).thenReturn(true);
	when(mockCustomer.getSumFromAccummulative()).thenReturn(sumOnDiscountCard);

	double mostExpensivePizzaPrice = 100;
	double expectedPrice = 235;
	double expectedDiscount = (0.3 * mostExpensivePizzaPrice)
		+ (expectedPrice - (0.3 * mostExpensivePizzaPrice)) * 0.30;
	List<Pizza> pizzasList = new ArrayList<Pizza>();

	pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
	pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
	pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
	pizzasList.add(new Pizza("Pizza4", mostExpensivePizzaPrice, Pizza.type.VEGETARIAN));

	order = new Order(mockCustomer, pizzasList);
	assertTrue(Double.compare(order.getDiscount(), expectedDiscount) == 0);
    }
    
    
    @Test
    public void addPizzaOnePizzaTest(){
	Pizza pizza = new Pizza("Pizza1", 45, Pizza.type.MEAT);
	
	order = new Order(mockCustomer, Collections.emptyList());
	order.addPizza(pizza, 1);
	assertTrue(order.getPizzasAmount() == 1);
	
    }
    
    @Test
    public void addPizzaMoreThanTenPizzaTest(){
	Pizza pizza = new Pizza("Pizza1", 45, Pizza.type.MEAT);
	
	order = new Order(mockCustomer, Collections.emptyList());
	assertTrue(order.addPizza(pizza, 15) == 10);
	
    }

}
