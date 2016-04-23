package org.study.pizzaservice.domain.accumulativecard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.study.pizzaservice.domain.customer.Customer;

/**
 * Class that represents accumulative card entity in pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
@Component
@Scope("prototype")
@Entity
@Table(name = "AccumulativeCards")
public class AccumulativeCardImpl implements AccumulativeCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private double sum;
    
    @OneToOne
    private Customer customer;

    public AccumulativeCardImpl() {
    }

    /**
     * @return the id
     */
    @Override
    public Long getId() {
	return id;
    }

    /**
     * @return the sum
     */
    @Override
    public double getSum() {
	return sum;
    }

    /**
     * @param sum
     *            the sum to set
     */
    @Override
    public void setSum(double sum) {
	this.sum = sum;
    }

    /**
     * @param sum
     *            the sum to add to card
     */
    @Override
    public void addToCard(double sum) {
	this.sum += sum;
    }

    @Override
    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

    @Override
    public Customer getCustomer() {
	return customer;
    }

}
