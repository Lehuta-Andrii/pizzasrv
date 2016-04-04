package org.study.pizzaservice.infrastructure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Set;

public class BeanchMarkHandler implements InvocationHandler {

    private Set<String> methods;
    private Object target;

    public BeanchMarkHandler(Set<String> methods, Object target) {
	this.methods = methods;
	this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

	Object result = null;
	if (methods.contains(method.getName())) {
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
