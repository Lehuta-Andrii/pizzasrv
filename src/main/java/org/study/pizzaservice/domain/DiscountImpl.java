package org.study.pizzaservice.domain;

public class DiscountImpl implements Discount {

	@Override
	public double getDiscount(Order order) {
		double result = 0;
		double price = order.getPrice();
		double mostExpensivePizza = 0;
		
		for(Pizza pizza: order.getPizzas()){
			if(pizza.getPrice() > mostExpensivePizza){
				mostExpensivePizza = pizza.getPrice();
			}
		}
		
		if (order.getPizzasAmount() >= 4) {
			result += mostExpensivePizza * 0.30;
			price -= result;
		}

		if (order.getCustomer().hasAccumulativeCard()) {
			double AccummulativeCardDiscount = order.getCustomer().getAccumulativeCard().getSum() * 0.1;

			if (Double.compare(AccummulativeCardDiscount, price * 0.3) < 0) {
				result += AccummulativeCardDiscount;
			} else {
				result += price * 0.3;
			}
		}

		return result;

	}

}
