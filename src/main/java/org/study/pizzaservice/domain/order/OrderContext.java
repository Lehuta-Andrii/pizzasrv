package org.study.pizzaservice.domain.order;

import java.util.ArrayList;
import java.util.List;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.discount.Discount;

/**
 * Class SimpleNoStateOrder keeps all information about order except of the
 * state of order
 * 
 * @author Andrii Lehuta
 *
 */
public class OrderContext {

    private static Long GID = new Long(0);

    private Long id;
    private Customer customer;
    private List<Pizza> pizzas;
    private Discount discount;

    public OrderContext(Customer customer, List<Pizza> pizzas) {
	id = GID++;
	this.customer = customer;
	this.pizzas = new ArrayList<Pizza>(pizzas);

    }

    /**
     * Set the object that will calculate discount in order
     * 
     * @param discount
     *            Discount object to set
     */
    public void setDiscount(Discount discount) {
	this.discount = discount;
    }

    /**
     * @return the amount of pizza objects in order
     */
    public int getPizzasAmount() {
	return pizzas.size();
    }

    /**
     * Add one pizza object to order
     * 
     * @param pizza
     *            pizza object to add
     * @return true if pizza object was added
     */
    public boolean addPizza(Pizza pizza) {
	return pizzas.add(pizza);
    }

    /**
     * Remove one pizza object to order
     * 
     * @param pizza
     *            pizza object to add
     * @return true if pizza object was removed
     */

    public boolean removePizza(Pizza pizza) {
	return pizzas.remove(pizza);

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

    /**
     * @return the DiscountObject of order
     */
    public Discount getDiscountObject() {
	return discount;
    }

    @Override
    public String toString() {
	return "Order [id=" + id + ", customer=" + customer + ", pizzas=" + pizzas + "]";
    }
}
