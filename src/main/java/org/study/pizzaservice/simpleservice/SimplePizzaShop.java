package org.study.pizzaservice.simpleservice;

import java.util.List;

import org.study.pizzaservice.AccumulativeCardService;
import org.study.pizzaservice.CustomerService;
import org.study.pizzaservice.DiscountService;
import org.study.pizzaservice.OrderService;
import org.study.pizzaservice.PizzaShopTemplate;
import org.study.pizzaservice.PizzasService;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.domain.order.state.CanceledState;
import org.study.pizzaservice.domain.order.state.DoneState;
import org.study.pizzaservice.domain.order.state.InProgressState;
import org.study.pizzaservice.domain.order.state.NewState;

public class SimplePizzaShop extends PizzaShopTemplate {

	private CustomerService customerService;
	private OrderService orderService;
	private DiscountService discountService;
	private AccumulativeCardService cardService;
	private PizzasService pizzasService;

	public SimplePizzaShop(CustomerService customerService, OrderService orderService, DiscountService discountService,
			AccumulativeCardService cardService, PizzasService pizzasService) {
		super();
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
	public Order makeOrder(Integer... pizzaIds) {
		return orderService.placeNewOrder(null, pizzaIds);
	}

	@Override
	public Order makeOrder(Customer customer, Integer... pizzaIds) {
		return orderService.placeNewOrder(customer, pizzaIds);
	}

	@Override
	public boolean confirmOrder(Order order) {
		if(order.getState().getClass().equals(NewState.class)){
			order.setState(new InProgressState());
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean cancelOrder(Order order) {
		if(!order.getState().getClass().equals(DoneState.class)){
			order.setState(new CanceledState());
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean accomplishOrder(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDiscount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
