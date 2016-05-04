package org.study.pizzaservice.domain.customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;

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
	// @GeneratedValue(strategy = GenerationType.SEQUENCE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	private String name;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Address> addresses = new ArrayList<Address>();

	public Customer() {
	}

	public Customer(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Customer(Long id, String name, List<Address> addresses) {
		this(id, name);
		this.addresses = addresses;
	}

	public Customer(String name, List<Address> addresses) {
		this.name = name;
		this.addresses = addresses;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", addresses=" + addresses + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addresses == null) ? 0 : addresses.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		Customer other = (Customer) obj;
		if (addresses == null) {
			if (other.addresses != null)
				return false;
		} else if (addresses.size() != other.addresses.size()){
			return false;
		} else if(!addresses.isEmpty() && !other.addresses.isEmpty() && Collections.disjoint(addresses, other.addresses)){
			return false;
		}
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}
