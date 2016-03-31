package org.study.pizzaservice.domain.customer;

/**
 * Immutable class that represents address object
 * 
 * @author Andrii Lehuta
 *
 */
public final class Address {
    private String city;
    private String street;
    private String house;
    private String room;

    public Address(String city, String street, String house, String room) {
	this.city = city;
	this.street = street;
	this.house = house;
	this.room = room;
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
