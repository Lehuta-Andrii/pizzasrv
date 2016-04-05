package org.study.pizzaservice.domain.customer;

public class AccumulativeCard {

    private static int GID = 0;

    private int id;
    private double sum;
    private Customer customer;

    public AccumulativeCard() {
	this.id = GID++;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
	return customer;
    }

    /**
     * @param customer
     *            the customer to set
     */
    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

    /**
     * @return the id
     */
    public int getId() {
	return id;
    }

    /**
     * @return the sum
     */
    public double getSum() {
	return sum;
    }

    /**
     * @param sum
     *            the sum to set
     */
    public void setSum(double sum) {
	this.sum = sum;
    }

    /**
     * @param sum
     *            the sum to add to card
     */
    public void addToCard(double sum) {
	this.sum += sum;
    }

}
