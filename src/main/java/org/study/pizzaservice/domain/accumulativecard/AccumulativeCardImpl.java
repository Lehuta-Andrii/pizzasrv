package org.study.pizzaservice.domain.accumulativecard;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Class that represents accumulative card entity in pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
@Component
@Scope("prototype")
public class AccumulativeCardImpl implements AccumulativeCard {

	private static int GID = 0;

	private Integer id;
	private double sum;

	public AccumulativeCardImpl() {
		this.id = GID++;
	}

	/**
	 * @return the id
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * @return the sum
	 */
	@Override
	public double getSum() {
		return sum;
	}

	/**
	 * @param sum
	 *            the sum to set
	 */
	@Override
	public void setSum(double sum) {
		this.sum = sum;
	}

	/**
	 * @param sum
	 *            the sum to add to card
	 */
	@Override
	public void addToCard(double sum) {
		this.sum += sum;
	}

}
