package org.study.pizzaservice.domain.accumulativecard;

import org.study.pizzaservice.domain.customer.Customer;

/**
 * Interface for definition of accumulative card entity in pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
public interface AccumulativeCard {

	Long getId();
	
	void setId(Long id);

	double getSum();

	void setSum(double sum);

	void addToCard(double sum);
	
	void setCustomer(Customer customer);
	
	Customer getCustomer();

}
