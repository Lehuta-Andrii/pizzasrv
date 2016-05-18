package org.study.pizzaservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Class that represents pizza entity in pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
@Entity
@Table(name = "pizzas")
@NamedQueries({ @NamedQuery(name = "Pizza.getPizzas", query = "SELECT p FROM Pizza p") })
public class Pizza {

	public enum Type {
		VEGETARIAN, SEA, MEAT;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private Double price;

	@Enumerated(EnumType.STRING)
	@Column(name = "pizzatype")
	private Type pizzaType;

	public Pizza() {
	}

	public Pizza(Long id, String name, double price, Type pizzaType) {

		this.id = id;
		this.name = name;
		this.price = price;
		this.pizzaType = pizzaType;
	}

	public Pizza(String name, double price, Type pizzaType) {
		this(null, name, price, pizzaType);
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

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the pizzaType
	 */
	public Type getPizzaType() {
		return pizzaType;
	}

	/**
	 * @param pizzaType
	 *            the pizzaType to set
	 */
	public void setPizzaType(Type pizzaType) {
		this.pizzaType = pizzaType;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pizzaType == null) ? 0 : pizzaType.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		Pizza other = (Pizza) obj;
		if (id.compareTo(other.id) != 0) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (pizzaType != other.pizzaType) {
			return false;
		}
		if (price == null) {
			if (other.price != null) {
				return false;
			}
		} else if (!price.equals(other.price)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pizza [id=" + id + ", name=" + name + ", price=" + price + ", pizzaType=" + pizzaType + "]";
	}

}
