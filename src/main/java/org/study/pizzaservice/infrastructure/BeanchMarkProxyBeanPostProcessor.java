package org.study.pizzaservice.infrastructure;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class BeanchMarkProxyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String arg1) throws BeansException {

		for (Method method : bean.getClass().getMethods()) {
			if (method.isAnnotationPresent(BeanchMark.class)) {
				return Proxy.newProxyInstance(this.getClass().getClassLoader(), getAllInterfaces(bean.getClass()),
						new BeanchMarkHandler(bean));
			}
		}

		return bean;
	}

	private Class<?>[] getAllInterfaces(Class<?> bean) {

		if (bean.equals(Object.class)) {
			return new Class<?>[0];
		}

		List<Class<?>> result = new ArrayList<Class<?>>(Arrays.asList(bean.getInterfaces()));

		result.addAll(Arrays.asList(getAllInterfaces(bean.getSuperclass())));

		return result.toArray(new Class<?>[0]);

	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String arg1) throws BeansException {
		return bean;
	}

}
