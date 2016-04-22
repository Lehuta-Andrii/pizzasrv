package org.study.pizzaservice.domain.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Immutable class that represents address object
 * 
 * @author Andrii Lehuta
 *
 */
@Entity
@Table(name ="addresses")
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable = false)
    private Long id;
    
    private String city;
    private String street;
    private String house;
    private String flat;
    private String phoneNumber;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "costumer_id")
    private Customer customer;

    protected Address(){}
    
    public Address(Customer customer, String city, String street, String house, String flat, String phone) {
	this.customer = customer;
	this.city = city;
	this.street = street;
	this.house = house;
	this.flat = flat;
	this.phoneNumber = phone;
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
     * @return the city
     */
    public String getCity() {
	return city;
    }

    /**
     * @return the street
     */
    public String getStreet() {
	return street;
    }

    /**
     * @return the house
     */
    public String getHouse() {
	return house;
    }

    /**
     * @return the room
     */
    public String getRoom() {
	return flat;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((city == null) ? 0 : city.hashCode());
	result = prime * result + ((house == null) ? 0 : house.hashCode());
	result = prime * result + ((flat == null) ? 0 : flat.hashCode());
	result = prime * result + ((street == null) ? 0 : street.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Address other = (Address) obj;
	if (city == null) {
	    if (other.city != null) {
		return false;
	    }
	} else if (!city.equals(other.city)) {
	    return false;
	}
	if (house == null) {
	    if (other.house != null) {
		return false;
	    }
	} else if (!house.equals(other.house)) {
	    return false;
	}
	if (flat == null) {
	    if (other.flat != null) {
		return false;
	    }
	} else if (!flat.equals(other.flat)) {
	    return false;
	}
	if (street == null) {
	    if (other.street != null) {
		return false;
	    }
	} else if (!street.equals(other.street)) {
	    return false;
	}
	return true;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
