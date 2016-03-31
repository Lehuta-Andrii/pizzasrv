package org.study.pizzaservice.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {

	private static Long GID = new Long(0);
	private static int MAX_AMOUNT_OF_PIZZAS = 10;

	public enum Status {
		NEW, IN_PROGRESS, CANCELLED, DONE;
	}

	private Long id;
	private Customer customer;
	private List<Pizza> pizzas;
	private Discount discount;
	private Status orderStatus = Status.NEW;

	public Order(Customer customer, List<Pizza> pizzas) {
		id = GID++;
		this.customer = customer;
		this.pizzas = new ArrayList<Pizza>(pizzas);

	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public int getPizzasAmount() {
		return pizzas.size();
	}

	public boolean addPizza(Pizza pizza) {
		if (pizzas.size() < MAX_AMOUNT_OF_PIZZAS && orderStatus == Status.NEW) {
			pizzas.add(pizza);
			return true;
		} else {
			return false;
		}
	}

	public boolean removePizza(Pizza pizza) {
		if (orderStatus == Status.NEW) {
			return pizzas.remove(pizza);
		} else {
			return false;
		}
	}

	public Status getStatus() {
		return orderStatus;
	}

	public boolean confirmOrder() {
		if (orderStatus == Status.NEW) {
			orderStatus = Status.IN_PROGRESS;
			return true;
		}
	
		return false;
	}

	public boolean cancelOrder() {
		if (orderStatus == Status.NEW || orderStatus == Status.IN_PROGRESS) {
			orderStatus = Status.CANCELLED;
			return true;
		}
		
		return false;
	}

	public boolean completeOrder() {
		if (orderStatus == Status.IN_PROGRESS) {
			orderStatus = Status.DONE;
			if (customer.hasAccumulativeCard()) {
				customer.getAccumulativeCard().addToCard(getPriceWithDiscount());
			}
			
			return true;
		}
		
		return false;
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
		return pizzas;
	}

	/**
	 * @param pizzas
	 *            the pizzas to set
	 */
	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	public double getDiscount() {
		if (discount != null) {
			return discount.getDiscount(this);
		}

		return 0;
	}

	public double getPrice() {
		double price = 0;

		for (Pizza pizza : pizzas) {
			price += pizza.getPrice();
		}

		return price;
	}

	public double getPriceWithDiscount() {
		return getPrice() + getDiscount();
	}
}
