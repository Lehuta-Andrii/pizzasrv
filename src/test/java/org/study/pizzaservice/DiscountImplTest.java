package org.study.pizzaservice;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCardImpl;
import org.study.pizzaservice.domain.discount.Discount;
import org.study.pizzaservice.domain.discount.DiscountImpl;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DiscountImplTest {

    @Mock
    private AccumulativeCardImpl mockAccumulativeCard;

    private Discount discount;

    @Before
    public void setUp() {
	discount = new DiscountImpl();
    }

    @Test
    public void getDiscountWithPizzasLessThenFourWithoutAccumCardTest() {

	Map<Pizza,Integer> pizzasList = new HashMap<Pizza, Integer>();

	pizzasList.put(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT), 1);
	pizzasList.put(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA), 1);
	pizzasList.put(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN), 1);

	assertTrue(Double.compare(discount.getDiscount(pizzasList, Optional.<AccumulativeCard> empty()), 0) == 0);
    }

    @Test
    public void getDiscountWithFourPizzasWithoutAccumCardTest() {

	double priceOfMostExpensivePizza = 100;
	double expectedDiscount = priceOfMostExpensivePizza * 0.3;
	Map<Pizza,Integer> pizzasList = new HashMap<Pizza, Integer>();

	pizzasList.put(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT), 1);
	pizzasList.put(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA), 1);
	pizzasList.put(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN), 1);
	pizzasList.put(new Pizza(3l,"Pizza4", priceOfMostExpensivePizza, Pizza.Type.VEGETARIAN), 1);

	assertTrue(Double.compare(discount.getDiscount(pizzasList, Optional.<AccumulativeCard> empty()),
		expectedDiscount) == 0);
    }

    @Test
    public void getDiscountWithPizzasLessThanFourWithSmallAccumCardSumTest() {

	double sumOnAccumulativeCard = 100;
	double expectedDiscount = sumOnAccumulativeCard * 0.1;

	Map<Pizza,Integer> pizzasList = new HashMap<Pizza, Integer>();

	pizzasList.put(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT), 1);
	pizzasList.put(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA), 1);
	pizzasList.put(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN), 1);

	when(mockAccumulativeCard.getSum()).thenReturn(sumOnAccumulativeCard);

	assertTrue(
		Double.compare(discount.getDiscount(pizzasList, Optional.<AccumulativeCard> of(mockAccumulativeCard)),
			expectedDiscount) == 0);
    }

    @Test
    public void getDiscountWithPizzasLessThanFourWithLargeAccumCardSumTest() {

	double pizzasPrice = 135;
	double sumOnAccumulativeCard = 100000;
	double expectedDiscount = pizzasPrice * 0.3;

	Map<Pizza,Integer> pizzasList = new HashMap<Pizza, Integer>();

	pizzasList.put(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT), 1);
	pizzasList.put(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA), 1);
	pizzasList.put(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN), 1);

	when(mockAccumulativeCard.getSum()).thenReturn(sumOnAccumulativeCard);

	assertTrue(
		Double.compare(discount.getDiscount(pizzasList, Optional.<AccumulativeCard> of(mockAccumulativeCard)),
			expectedDiscount) == 0);
    }

    @Test
    public void getDiscountWithPizzasMoreThanFourWithSmallAccumCardSumTest() {

	double sumOnAccumulativeCard = 100;
	double priceOfMostExpensivePizza = 100;
	double expectedDiscount = (0.3 * priceOfMostExpensivePizza) + sumOnAccumulativeCard * 0.1;
	Map<Pizza,Integer> pizzasList = new HashMap<Pizza, Integer>();

	pizzasList.put(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT), 1);
	pizzasList.put(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA), 1);
	pizzasList.put(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN), 1);
	pizzasList.put(new Pizza(3l,"Pizza4", priceOfMostExpensivePizza, Pizza.Type.VEGETARIAN), 1);

	when(mockAccumulativeCard.getSum()).thenReturn(sumOnAccumulativeCard);

	assertTrue(
		Double.compare(discount.getDiscount(pizzasList, Optional.<AccumulativeCard> of(mockAccumulativeCard)),
			expectedDiscount) == 0);
    }

    @Test
    public void getDiscountWithPizzasMoreThanFourWithLargeAccumCardSumTest() {

	double pizzasPrice = 235;
	double sumOnAccumulativeCard = 1000000;
	double priceOfMostExpensivePizza = 100;
	double expectedDiscount = (0.3 * priceOfMostExpensivePizza)
		+ (pizzasPrice - (0.3 * priceOfMostExpensivePizza)) * 0.30;
	Map<Pizza,Integer> pizzasList = new HashMap<Pizza, Integer>();

	pizzasList.put(new Pizza(0l, "Pizza1", 45, Pizza.Type.MEAT), 1);
	pizzasList.put(new Pizza(1l, "Pizza2", 45, Pizza.Type.SEA), 1);
	pizzasList.put(new Pizza(2l, "Pizza3", 45, Pizza.Type.VEGETARIAN), 1);
	pizzasList.put(new Pizza(3l,"Pizza4", priceOfMostExpensivePizza, Pizza.Type.VEGETARIAN), 1);

	when(mockAccumulativeCard.getSum()).thenReturn(sumOnAccumulativeCard);

	assertTrue(
		Double.compare(discount.getDiscount(pizzasList, Optional.<AccumulativeCard> of(mockAccumulativeCard)),
			expectedDiscount) == 0);
    }

}
