package org.study.pizzaservice;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.discount.Discount;
import org.study.pizzaservice.domain.discount.DiscountImpl;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DiscountImplTest {
	
	@Mock
	private AccumulativeCard mockAccumulativeCard;

	private Discount discount;

	@Before
	public void setUp() {
		discount = new DiscountImpl();
	}

	@Test
	public void getDiscountWithPizzasLessThenFourWithoutAccumCardTest() {

		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

		assertTrue(Double.compare(discount.getDiscount(pizzasList, null), 0) == 0);
	}

	@Test
	public void getDiscountWithFourPizzasWithoutAccumCardTest() {

		double priceOfMostExpensivePizza = 100;
		double expectedDiscount = priceOfMostExpensivePizza * 0.3;
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(new Pizza("Pizza4", priceOfMostExpensivePizza, Pizza.type.VEGETARIAN));

		assertTrue(Double.compare(discount.getDiscount(pizzasList, null), expectedDiscount) == 0);
	}

	@Test
	public void getDiscountWithPizzasLessThanFourWithSmallAccumCardSumTest() {

		double sumOnAccumulativeCard = 100;
		double expectedDiscount = sumOnAccumulativeCard * 0.1;

		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

		when(mockAccumulativeCard.getSum()).thenReturn(sumOnAccumulativeCard);

		assertTrue(Double.compare(discount.getDiscount(pizzasList, mockAccumulativeCard), expectedDiscount) == 0);
	}

	@Test
	public void getDiscountWithPizzasLessThanFourWithLargeAccumCardSumTest() {

		double pizzasPrice = 135;
		double sumOnAccumulativeCard = 100000;
		double expectedDiscount = pizzasPrice * 0.3;

		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

		when(mockAccumulativeCard.getSum()).thenReturn(sumOnAccumulativeCard);

		assertTrue(Double.compare(discount.getDiscount(pizzasList, mockAccumulativeCard), expectedDiscount) == 0);
	}
	
	@Test
	public void getDiscountWithPizzasMoreThanFourWithSmallAccumCardSumTest() {

		double sumOnAccumulativeCard = 100;
		double priceOfMostExpensivePizza = 100;
		double expectedDiscount = (0.3 * priceOfMostExpensivePizza) + sumOnAccumulativeCard * 0.1;
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(new Pizza("Pizza4", priceOfMostExpensivePizza, Pizza.type.VEGETARIAN));

		when(mockAccumulativeCard.getSum()).thenReturn(sumOnAccumulativeCard);

		assertTrue(Double.compare(discount.getDiscount(pizzasList, mockAccumulativeCard), expectedDiscount) == 0);
	}

	@Test
	public void getDiscountWithPizzasMoreThanFourWithLargeAccumCardSumTest() {

		double pizzasPrice = 235;
		double sumOnAccumulativeCard = 1000000;
		double priceOfMostExpensivePizza = 100;
		double expectedDiscount = (0.3 * priceOfMostExpensivePizza)
				+ (pizzasPrice - (0.3 * priceOfMostExpensivePizza)) * 0.30;
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(new Pizza("Pizza4", priceOfMostExpensivePizza, Pizza.type.VEGETARIAN));

		when(mockAccumulativeCard.getSum()).thenReturn(sumOnAccumulativeCard);

		assertTrue(Double.compare(discount.getDiscount(pizzasList, mockAccumulativeCard), expectedDiscount) == 0);
	}

}
