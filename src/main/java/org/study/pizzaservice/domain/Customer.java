package org.study.pizzaservice.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;

public class Customer implements FactoryBean<Customer>{

    private static int GID = 0;
    private int id;
    private String name;
    private double accumulativeCard = -1;
    List<Address> addresses = new ArrayList<Address>();

    public Customer(String name) {
	this.id = GID++;
	this.name = name;
    }

    public Customer(){
	
    }
    /**
     * @return the adresses
     */
    public List<Address> getAddresses() {
	return addresses;
    }

    /**
     * @param adresses
     *            the adresses to set
     */
    public void setAdresses(List<Address> adresses) {
	this.addresses = adresses;
    }

    public boolean addAddress(Address address) {
	if (addresses.contains(address)) {
	    return false;
	} else {
	    addresses.add(address);
	    return true;
	}
    }

    public boolean removeAddress(Address address) {
	return addresses.remove(address);
    }

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

}
