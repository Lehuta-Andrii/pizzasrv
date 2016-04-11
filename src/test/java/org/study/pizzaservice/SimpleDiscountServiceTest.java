package org.study.pizzaservice;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.study.pizzaservice.domain.discount.Discount;
import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.simpleservice.SimpleDiscountService;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SimpleDiscountServiceTest {

    @Mock
    private Discount mockDiscount;

    private DiscountService discountService;

    @Before
    public void setUp() {
	discountService = new SimpleDiscountService();
    }

    @Test
    public void addDiscountTestWhenDiscountIsInService() {

	discountService.setDiscounts(Collections.singletonList(mockDiscount));

	assertFalse(discountService.addDiscount(mockDiscount));

    }

    @Test
    public void addDiscountTestWhenDiscountIsNotInService() {

	assertTrue(discountService.addDiscount(mockDiscount));

    }

    @Test
    public void countDiscountTestWithNoDiscount() {
	double resultingDiscount = 0;
	
	discountService.setDiscounts(Collections.emptyList());
		
	assertTrue(Double.compare(discountService.countDiscount(null, null), resultingDiscount) == 0);

    }

    
    @Test
    public void countDiscountTestWithOneDiscount() {
	double resultingDiscount = 50;
	Order mockOrder = mock(Order.class);
	
	discountService.setDiscounts(Collections.singletonList(mockDiscount));
	
	when(mockOrder.getPizzas()).thenReturn(null);
	when(mockDiscount.getDiscount(any(), any())).thenReturn(resultingDiscount);

	assertTrue(Double.compare(discountService.countDiscount(mockOrder, null), resultingDiscount) == 0);

    }

    @Test
    public void countDiscountTestWithMoreThanOneDiscount() {
	double resultingDiscount = 100;
	Order mockOrder = mock(Order.class);
	Discount otherMockDiscount = mock(Discount.class);
	
	discountService.setDiscounts(Collections.singletonList(mockDiscount));
	discountService.addDiscount(otherMockDiscount);
	
	when(mockOrder.getPizzas()).thenReturn(null);
	when(mockDiscount.getDiscount(any(), any())).thenReturn(resultingDiscount);
	when(otherMockDiscount.getDiscount(any(), any())).thenReturn(resultingDiscount);
	
	assertTrue(Double.compare(discountService.countDiscount(mockOrder, null), resultingDiscount*2) == 0);

    }

    @Test
    public void countDiscountWithCertainDiscountServiceTest() {
	double resultingDiscount = 100;
	Order mockOrder = mock(Order.class);
	Discount otherMockDiscount = mock(Discount.class);
	
	discountService.setDiscounts(Collections.singletonList(mockDiscount));
	discountService.addDiscount(otherMockDiscount);
	
	when(mockOrder.getPizzas()).thenReturn(null);
	when(mockDiscount.getDiscount(any(), any())).thenReturn(resultingDiscount);
	when(otherMockDiscount.getDiscount(any(), any())).thenReturn(resultingDiscount);
	
	assertTrue(Double.compare(discountService.countDiscount(mockOrder, null, Collections.singletonList(otherMockDiscount)), resultingDiscount) == 0);

    }

    @Test
    public void countDiscountWithNoDiscountServiceTest() {
	double resultingDiscount = 0;
	Order mockOrder = mock(Order.class);
	Discount otherMockDiscount = mock(Discount.class);
	
	discountService.setDiscounts(Collections.singletonList(mockDiscount));
	discountService.addDiscount(otherMockDiscount);
	
	when(mockOrder.getPizzas()).thenReturn(null);
	when(mockDiscount.getDiscount(any(), any())).thenReturn(resultingDiscount);
	when(otherMockDiscount.getDiscount(any(), any())).thenReturn(resultingDiscount);
	
	assertTrue(Double.compare(discountService.countDiscount(mockOrder, null, Collections.EMPTY_LIST), resultingDiscount) == 0);

    }

}
