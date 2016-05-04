package org.study.pizzaservice.domain.order;

import java.time.LocalDate;
import java.util.Map;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;

/**
 * Interface for definition of all states of order
 * 
 * @author Andrii Lehuta
 *
 */
public interface OrderState {

    void setContext(OrderContext order);

    int getPizzasAmount(); 

    boolean addPizza(Pizza pizza, int amount);
    
    boolean removePizza(Pizza pizza, int amount);
    
    int getAmountOfPizza(Pizza pizza); 

    Customer getCustomer(); 

    void setCustomer(Customer customer); 

    Map<Pizza, Integer> getPizzasMap(); 
    
    void setPizzasMap(Map<Pizza, Integer> pizzaMap);
    
    boolean canSetState(OrderState state);

    public LocalDate getDate(); 

  	public void setDate(LocalDate date); 

	public Address getAddress(); 

	public void setAddress(Address address); 
	
  	
}
