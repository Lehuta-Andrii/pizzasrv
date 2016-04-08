package org.study.pizzaservice.domain.accumulativecard;

/**
 * Interface for definition of accumulative card entity in pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
public interface AccumulativeCard {

	int getId();

	double getSum();

	void setSum(double sum);

	void addToCard(double sum);

}
