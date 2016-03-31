package org.study.pizzaservice.domain.customer;

public class AccumulativeCard {

	private static int GID = 0;

	public int id;
	public double sum;

	public AccumulativeCard() {
		this.id = GID++;
	}

		
	/**
	 * @return the sum
	 */
	public double getSum() {
	    return sum;
	}


	/**
	 * @param sum the sum to set
	 */
	public void setSum(double sum) {
	    this.sum = sum;
	}

	/**
	 * @param sum the sum to add to card
	 */
	public void addToCard(double sum){
		this.sum += sum;
	}

}
