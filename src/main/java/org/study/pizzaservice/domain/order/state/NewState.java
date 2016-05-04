package org.study.pizzaservice.domain.order.state;

import java.time.LocalDate;
import java.util.Map;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.OrderContext;
import org.study.pizzaservice.domain.order.OrderState;

/**
 * Class object that represents New State of order. Dispatches all calls to
 * SimpleNoStateOrder object.
 * 
 * @author Andrii Lehuta
 *
 */
public class NewState implements OrderState {

    private OrderContext order;

    public void setContext(OrderContext order) {
	this.order = order;
    }

    @Override
    public int getPizzasAmount() {
	return order.getPizzasAmount();
    }

    @Override
    public boolean addPizza(Pizza pizza, int amount) {
	return order.addPizza(pizza, amount);
    }

    @Override
    public boolean removePizza(Pizza pizza, int amount) {
	return order.removePizza(pizza, amount);
    }

    @Override
    public Customer getCustomer() {
	return order.getCustomer();
    }

    @Override
    public void setCustomer(Customer customer) {
	order.setCustomer(customer);
    }

    @Override
    public boolean canSetState(OrderState state) {
	    return true;
    }

    @Override
    public LocalDate getDate() {
    	return order.getDate();
    }
    
    @Override
    public void setDate(LocalDate date) {
    	order.setDate(date);
    }
    
    @Override
    public Address getAddress() {
    	return order.getAddress();
    }
    
    @Override
    public void setAddress(Address address) {
    	order.setAddress(address);
    }
    
	@Override
	public Map<Pizza, Integer> getPizzasMap() {
		return order.getPizzasMap();
	}

	@Override
	public void setPizzasMap(Map<Pizza, Integer> pizzaMap) {
		order.setPizzasMap(pizzaMap);
	}

	@Override
	public int getAmountOfPizza(Pizza pizza) {
		return order.getAmountOfPizza(pizza);
	}
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "NewState";
    }




    
}
