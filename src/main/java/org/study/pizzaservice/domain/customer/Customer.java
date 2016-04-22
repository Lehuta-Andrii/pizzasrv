package org.study.pizzaservice.domain.customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Customer class represents customer entity of pizza service
 * 
 * @author Lehuta Andrii
 *
 */
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @OneToMany(mappedBy="customer", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<Address>();

    public Customer() {
    }

    public Customer(Long id, String name) {
	this.id = id;
	this.name = name;
    }

    public Customer(Long id, String name, List<Address> addresses) {
	this.id = id;
	this.name = name;
	this.addresses = addresses;
    }

    /**
     * @return the adresses
     */
    public List<Address> getAddresses() {
	return Collections.unmodifiableList(addresses);
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
    public Long getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
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
