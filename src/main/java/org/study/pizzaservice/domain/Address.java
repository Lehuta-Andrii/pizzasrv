package org.study.pizzaservice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {

    @Column(name = "ADDR_ID")
    private Integer id;

    public Address() {
    }

    private String city;
    private String street;
    private String house;
    private String room;

    public Address(Integer id, String city, String street, String house, String room) {
	this.id = id;
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

}
