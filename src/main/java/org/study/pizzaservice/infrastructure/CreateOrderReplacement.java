package org.study.pizzaservice.infrastructure;

import java.lang.reflect.Method;

import org.springframework.beans.factory.support.MethodReplacer;
import org.study.pizzaservice.domain.Order;

public class CreateOrderReplacement implements MethodReplacer {

    @Override
    public Object reimplement(Object arg0, Method method, Object[] args) throws Throwable {
	System.out.println(method.getName());
	System.out.println("hello from proxy");
	return new Order();
    }

}
