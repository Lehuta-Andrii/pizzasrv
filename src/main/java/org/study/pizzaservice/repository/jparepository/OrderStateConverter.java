package org.study.pizzaservice.repository.jparepository;

import javax.persistence.*;
import org.study.pizzaservice.domain.order.OrderState;

@Converter
public class OrderStateConverter implements AttributeConverter<OrderState, String> {

	@Override
	public String convertToDatabaseColumn(OrderState state) {
		return state.getClass().getName();
	}

	@Override
	public OrderState convertToEntityAttribute(String state) {
		try {
			return (OrderState) Class.forName(state).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}

}
