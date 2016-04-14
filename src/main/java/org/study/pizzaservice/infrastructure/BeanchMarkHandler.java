package org.study.pizzaservice.infrastructure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BeanchMarkHandler implements InvocationHandler {

    private Object target;

    public BeanchMarkHandler(Object target) {
	this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

	Object result = null;

	if (target.getClass().getMethod(method.getName(), method.getParameterTypes())
		.isAnnotationPresent(BeanchMark.class)) {
	    long oldTime = System.nanoTime();
	    result = method.invoke(target, args);
	    long timeDifference = System.nanoTime() - oldTime;
	    System.out.println("Time of method " + method.getName() + " is " + timeDifference);
	    return result;
	} else {
	    return method.invoke(target, args);
	}

    }

}
