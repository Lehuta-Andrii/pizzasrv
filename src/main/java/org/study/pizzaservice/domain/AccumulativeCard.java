package org.study.pizzaservice.domain;

public class AccumulativeCard {

	private static int GID = 0;

	public int id;
	public double sum;

	public AccumulativeCard() {
		this.id = GID++;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}
	
	public void addToCard(double sum){
		this.sum += sum;
	}

}
