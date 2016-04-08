package org.study.pizzaservice.domain.customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Customer class represents customer entity of pizza service
 * 
 * @author Lehuta Andrii
 *
 */
public class Customer {

    private static int GID = 0;
    private Integer id;
    private String name;

    private List<Address> addresses = new ArrayList<Address>();

    public Customer(String name) {
	this.id = GID++;
	this.name = name;
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

    /**
     * Add one new Address to Customers addresses list
     * 
     * @param address
     *            the address to add
     * @return true if address was added
     */
    public boolean addAddress(Address address) {
	if (addresses.contains(address)) {
	    return false;
	} else {
	    addresses.add(address);
	    return true;
	}
    }

    /**
     * Remove one new Address to Customers addresses list
     * 
     * @param address
     *            the address to add
     * @return true if address was removed
     */
    public boolean removeAddress(Address address) {
	return addresses.remove(address);
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
    public String toString() {
	return "Customer [id=" + id + ", name=" + name + "]";
    }

}
