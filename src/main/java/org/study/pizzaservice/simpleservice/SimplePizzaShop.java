package org.study.pizzaservice.simpleservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.pizzaservice.AccumulativeCardService;
import org.study.pizzaservice.CustomerService;
import org.study.pizzaservice.DiscountService;
import org.study.pizzaservice.OrderService;
import org.study.pizzaservice.PizzaShopTemplate;
import org.study.pizzaservice.PizzasService;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.Order;

/**
 * Implementation of pizza shop template pattern
 * 
 * @author Andrii_Lehuta
 *
 */
@Service
public class SimplePizzaShop extends PizzaShopTemplate {

	private CustomerService customerService;
	private OrderService orderService;
	private DiscountService discountService;
	private AccumulativeCardService cardService;
	private PizzasService pizzasService;

	@Autowired
	public SimplePizzaShop(CustomerService customerService, OrderService orderService, DiscountService discountService,
			AccumulativeCardService cardService, PizzasService pizzasService) {
		this.customerService = customerService;
		this.orderService = orderService;
		this.discountService = discountService;
		this.cardService = cardService;
		this.pizzasService = pizzasService;
	}

	@Override
	public List<Pizza> showMenu() {
		return pizzasService.getPizzas();
	}

	@Override
	public Order makeOrder(Customer customer, Integer... pizzaIds) {
		return orderService.placeNewOrder(customer, pizzaIds);
	}

	@Override
	public boolean confirmOrder(Order order) {
		return orderService.confirmOrder(order);
	}

	@Override
	public boolean cancelOrder(Order order) {
		return orderService.cancelOrder(order);
	}

	@Override
	public boolean accomplishOrder(Order order) {
		boolean result = orderService.accomplishOrder(order);

		if (result) {
			cardService.addSumToCard(order.getCustomer(), getPriceWithDiscount(order));
		}

		return result;
	}

	@Override
	public double getPrice(Order order) {
		return order.getPrice();
	}

	@Override
	public double getDiscount(Order order) {
		return discountService.countDiscount(order, cardService.getCard(order.getCustomer()));
	}

	@Override
	public double getPriceWithDiscount(Order order) {
		return getPrice(order) + getDiscount(order);
	}

}
