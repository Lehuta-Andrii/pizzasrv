package org.study.pizzaservice;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.domain.order.state.CanceledState;
import org.study.pizzaservice.domain.order.state.DoneState;
import org.study.pizzaservice.domain.order.state.InProgressState;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest {

    @Mock
    private Customer mockCustomer;

    private Order order;

    @Test
    public void getPriceTestOnEmptyPizzaList() {
	List<Pizza> emptyPizzasList = new ArrayList<Pizza>();
	order = new Order(mockCustomer, emptyPizzasList);

	assertTrue(Double.compare(0, order.getPrice()) == 0);
    }

    @Test
    public void getPriceTestOnPizzaList() {

	double expectedPrice = 135;
	List<Pizza> pizzasList = new ArrayList<Pizza>();

	pizzasList.add(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT));
	pizzasList.add(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA));
	pizzasList.add(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN));

	order = new Order(mockCustomer, pizzasList);
	assertTrue(Double.compare(order.getPrice(), expectedPrice) == 0);
    }

    @Test
    public void addPizzaTestOnEmptyPizzaListAndNewOrder() {

	order = new Order(mockCustomer, Collections.<Pizza> emptyList());

	assertTrue(order.addPizza(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT)));
	assertTrue(order.getPizzasAmount() == 1);

    }

    @Test
    public void addPizzaTestOnPizzaListWithInProgressOrder() {

	List<Pizza> pizzasList = new ArrayList<Pizza>();

	pizzasList.add(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT));
	pizzasList.add(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA));
	pizzasList.add(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN));

	order = new Order(mockCustomer, pizzasList);
	int expectedPizzasAmount = order.getPizzasAmount();

	order.setState(new InProgressState());

	assertFalse(order.addPizza(new Pizza(0l, "Pizza11", 45, Pizza.Type.MEAT)));
	assertTrue(order.getPizzasAmount() == expectedPizzasAmount);

    }

    @Test
    public void addPizzaTestOnPizzaListInCancelledOrder() {

	List<Pizza> pizzasList = new ArrayList<Pizza>();

	pizzasList.add(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT));
	pizzasList.add(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA));
	pizzasList.add(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN));

	order = new Order(mockCustomer, pizzasList);
	int expectedPizzasAmount = order.getPizzasAmount();

	order.setState(new CanceledState());

	assertFalse(order.addPizza(new Pizza(0l, "Pizza11", 45, Pizza.Type.MEAT)));
	assertTrue(order.getPizzasAmount() == expectedPizzasAmount);

    }

    @Test
    public void addPizzaTestOnPizzaListInDoneOrder() {

	List<Pizza> pizzasList = new ArrayList<Pizza>();

	pizzasList.add(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT));
	pizzasList.add(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA));
	pizzasList.add(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN));

	order = new Order(mockCustomer, pizzasList);
	int expectedPizzasAmount = order.getPizzasAmount();

	order.setState(new DoneState());

	assertFalse(order.addPizza(new Pizza(0l, "Pizza11", 45, Pizza.Type.MEAT)));
	assertTrue(order.getPizzasAmount() == expectedPizzasAmount);

    }

    @Test
    public void removePizzaTestOnEmptyPizzaListAndNewOrder() {

	order = new Order(mockCustomer, Collections.<Pizza> emptyList());

	assertFalse(order.removePizza(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT)));
	assertTrue(order.getPizzasAmount() == 0);

    }

    @Test
    public void removePizzaTestOnPizzaListAndNewOrder() {

	List<Pizza> pizzasList = new ArrayList<Pizza>();

	Pizza specific = new Pizza(4l, "Specific Pizza", 45, Pizza.Type.VEGETARIAN);

	pizzasList.add(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT));
	pizzasList.add(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA));
	pizzasList.add(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN));
	pizzasList.add(specific);

	order = new Order(mockCustomer, pizzasList);
	int expectedPizzasAmount = order.getPizzasAmount() - 1;

	assertTrue(order.removePizza(specific));
	assertTrue(order.getPizzasAmount() == expectedPizzasAmount);
    }

    @Test
    public void removePizzaTestOnListOfPizzasAndInProgressOrder() {

	List<Pizza> pizzasList = new ArrayList<Pizza>();

	Pizza specific = new Pizza(4l, "Specific Pizza", 45, Pizza.Type.VEGETARIAN);

	pizzasList.add(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT));
	pizzasList.add(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA));
	pizzasList.add(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN));
	pizzasList.add(specific);

	order = new Order(mockCustomer, pizzasList);
	order.setState(new InProgressState());
	int expectedPizzasAmount = order.getPizzasAmount();

	assertFalse(order.removePizza(specific));
	assertTrue(order.getPizzasAmount() == expectedPizzasAmount);

    }

    @Test
    public void removePizzaTestOnListOfPizzasInCanceledOrder() {

	List<Pizza> pizzasList = new ArrayList<Pizza>();

	Pizza specific = new Pizza(4l, "Specific Pizza", 45, Pizza.Type.VEGETARIAN);

	pizzasList.add(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT));
	pizzasList.add(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA));
	pizzasList.add(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN));
	pizzasList.add(specific);

	order = new Order(mockCustomer, pizzasList);
	assertTrue(order.setState(new CanceledState()));
	int expectedPizzasAmount = order.getPizzasAmount();

	assertFalse(order.removePizza(specific));
	assertTrue(order.getPizzasAmount() == expectedPizzasAmount);

    }

    @Test
    public void removePizzaTestOnListLessThanTenPizzasAndInDoneOrder() {

	List<Pizza> pizzasList = new ArrayList<Pizza>();

	Pizza specific = new Pizza(4l, "Specific Pizza", 45, Pizza.Type.VEGETARIAN);

	pizzasList.add(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT));
	pizzasList.add(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA));
	pizzasList.add(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN));
	pizzasList.add(specific);

	order = new Order(mockCustomer, pizzasList);
	order.setState(new DoneState());
	int expectedPizzasAmount = order.getPizzasAmount();

	assertFalse(order.removePizza(specific));
	assertTrue(order.getPizzasAmount() == expectedPizzasAmount);

    }

}
