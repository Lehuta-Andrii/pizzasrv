package org.study.pizzaservice.domain;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

@Entity
public class Address  {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ADDR_ID")
    private Integer id;

    public Address() {
    }

    private String city;
    private String street;
    private String house;
    private String room;
    
    @ManyToOne(optional = false)
    @JoinTable(name = "CUST_ADDR")
    private Customer customer;
    
    @Column(name = "ADDR_STATE")
    @Convert(converter = StateConvertor.class)
    private State state;
    
    public Address( String city, String street, String house, String room) {
	this.city = city;
	this.street = street;
	this.house = house;
	this.room = room;
    }

    /**
     * @return the id
     */
    public Integer getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
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
	return room;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((city == null) ? 0 : city.hashCode());
	result = prime * result + ((house == null) ? 0 : house.hashCode());
	result = prime * result + ((room == null) ? 0 : room.hashCode());
	result = prime * result + ((street == null) ? 0 : street.hashCode());
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Address other = (Address) obj;
	if (city == null) {
	    if (other.city != null)
		return false;
	} else if (!city.equals(other.city))
	    return false;
	if (house == null) {
	    if (other.house != null)
		return false;
	} else if (!house.equals(other.house))
	    return false;
	if (room == null) {
	    if (other.room != null)
		return false;
	} else if (!room.equals(other.room))
	    return false;
	if (street == null) {
	    if (other.street != null)
		return false;
	} else if (!street.equals(other.street))
	    return false;
	return true;
    }

    /**
     * @return the state
     */
    public State getState() {
	return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
	this.state = state;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
