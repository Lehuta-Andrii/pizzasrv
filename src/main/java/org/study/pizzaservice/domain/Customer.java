package org.study.pizzaservice.domain;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.springframework.beans.factory.FactoryBean;

@Entity
public class Customer implements FactoryBean<Customer>{

    private static int GID = 0;
    
    @Id
    //@TableGenerator(name = "CUST", table = "hibernate_sequences")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    //@GeneratedValue(strategy = GenerationType.TABLE, generator = "CUST")
    private int id;
    private String name;
    private double accumulativeCard = -1;
    
    @ElementCollection
    private List<Address> addresses;
    
    @ElementCollection
    private List<String> phones;

    public Customer(String name) {
	this.id = GID++;
	this.name = name;
    }

    public Customer(){
	
    }

//    public boolean addAddress(Address address) {
//	if (addresses.contains(address)) {
//	    return false;
//	} else {
//	    addresses.add(address);
//	    return true;
//	}
//    }
//
//    public boolean removeAddress(Address address) {
//	return addresses.remove(address);
//    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Customer [id=" + id + ", name=" + name + "]";
    }

    public boolean hasAccumulativeCard() {
	return accumulativeCard != -1;
    }

    public void setAccumulativeCard() {
	if (hasAccumulativeCard()) {
	    accumulativeCard = 0;
	}
    }

    public void addToAcummulativeCard(double sum) {
	if (hasAccumulativeCard()) {
	    accumulativeCard += sum;
	}
    }

    public double getSumFromAccummulative() {
	return accumulativeCard;
    }

    /**
     * @return the id
     */
    public int getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
	this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    @Override
    public Customer getObject() throws Exception {
	return new Customer("nulll");
    }

    @Override
    public Class<?> getObjectType() {
	return this.getClass();
    }

    @Override
    public boolean isSingleton() {
	return false;
    }

    /**
     * @return the phones
     */
    public List<String> getPhones() {
        return phones;
    }

    /**
     * @param phones the phones to set
     */
    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<Address> getAddresses() {
	return addresses;
    }

    public void setAddresses(List<Address> addresses) {
	this.addresses = addresses;
    }

}
