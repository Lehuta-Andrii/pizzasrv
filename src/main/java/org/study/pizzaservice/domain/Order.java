package org.study.pizzaservice.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {

    private static Long GID = new Long(0);
    private static int MAX_AMOUNT_OF_PIZZAS = 10;

    public enum Status {
	NEW, IN_PROGRESS, CANCELLED, DONE;
    }

    private Long id;
    private Customer customer;
    private Map<Pizza, Integer> pizzas;
    private int pizzasNumber = 0;
    private double mostExpensivePizza = 0;
    private Status orderStatus = Status.NEW;

    public Order(Customer customer, List<Pizza> pizzas) {
	id = GID++;
	this.customer = customer;
	this.pizzas = new HashMap<Pizza, Integer>();

	for (Pizza pizza : pizzas) {
	    add(pizza, 1);
	}
    }

    public int getPizzasAmount() {
	return pizzasNumber;
    }

    public int setPizza(Pizza pizza, int amount) {

	if (amount <= MAX_AMOUNT_OF_PIZZAS && amount > 0) {

	    if (pizzas.containsKey(pizza)) {
		pizzasNumber -= pizzas.get(pizza);
	    }

	    pizzas.put(pizza, amount);
	    pizzasNumber += amount;
	    return amount;
	}

	return 0;
    }

    public int addPizza(Pizza pizza, int amount) {
	return add(pizza, amount);
    }

    private int add(Pizza pizza, int amount) {

	if (amount <= 0) {
	    return amount;
	}

	int result = 0;

	if (pizzas.containsKey(pizza)) {
	    if ((pizzas.get(pizza) + amount) > MAX_AMOUNT_OF_PIZZAS) {
		pizzas.put(pizza, MAX_AMOUNT_OF_PIZZAS);
		result = amount - (MAX_AMOUNT_OF_PIZZAS - (pizzas.get(pizza) + amount));
	    } else {
		pizzas.put(pizza, pizzas.get(pizza) + amount);
		result = amount;
	    }
	} else {
	    if (amount > MAX_AMOUNT_OF_PIZZAS) {
		pizzas.put(pizza, amount);
		result = MAX_AMOUNT_OF_PIZZAS;
	    } else {
		pizzas.put(pizza, amount);
		result = amount;
	    }
	}

	if (pizza.getPrice() > mostExpensivePizza) {
	    mostExpensivePizza = pizza.getPrice();
	}

	pizzasNumber += amount;

	return result;
    }

    public Status getStatus() {
	return orderStatus;
    }

    public void confirmOrder() {
	if (orderStatus == Status.NEW) {
	    orderStatus = Status.IN_PROGRESS;
	}
    }

    public void cancelOrder() {
	if (orderStatus == Status.NEW || orderStatus == Status.IN_PROGRESS) {
	    orderStatus = Status.CANCELLED;
	}
    }

    public void completeOrder() {
	if (orderStatus == Status.IN_PROGRESS) {
	    orderStatus = Status.DONE;
	    if (customer.hasAccumulativeCard()) {
		customer.addToAcummulativeCard(countPrice());
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Order [id=" + id + ", customer=" + customer + ", pizzas=" + pizzas + "]";
    }

    /**
     * @return the id
     */
    public Long getId() {
	return id;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
	return customer;
    }

    /**
     * @param customer
     *            the customer to set
     */
    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

    /**
     * @return the pizzas
     */
    public List<Pizza> getPizzas() {
	return new ArrayList<Pizza>(pizzas.keySet());
    }

    /**
     * @param pizzas
     *            the pizzas to set
     */
    public void setPizzas(List<Pizza> pizzas) {
	// this.pizzas = pizzas;
    }

    public double getDiscount() {
	double result = 0;
	double price = countPrice();

	if (pizzasNumber >= 4) {
	    result += mostExpensivePizza * 0.30;
	    price -= result;
	}

	if (customer.hasAccumulativeCard()) {
	    double AccummulativeCardDiscount = customer.getSumFromAccummulative() * 0.1;

	    if (Double.compare(AccummulativeCardDiscount, price * 0.3) < 0) {
		result += AccummulativeCardDiscount;
	    } else {
		result += price * 0.3;
	    }
	}

	return result;
    }

    private double countPrice() {
	double price = 0;

	for (Pizza pizza : pizzas.keySet()) {
	    price += pizza.getPrice() * pizzas.get(pizza);
	}

	return price;

    }

    public double getPrice() {
	return countPrice();
    }

    public double getPriceWithDiscount() {
	return getPrice() - getDiscount();
    }
}
