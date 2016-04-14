package org.study.pizzaservice.infrastructure;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class BeanchMarkProxyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String arg1) throws BeansException {

	List<Class<?>> interfaces = new ArrayList<Class<?>>(Arrays.asList(bean.getClass().getInterfaces()));
	interfaces.addAll(Arrays.asList(bean.getClass().getSuperclass().getInterfaces()));

	for (Method method : bean.getClass().getMethods()) {
	    if (method.isAnnotationPresent(BeanchMark.class)) {
		return Proxy.newProxyInstance(this.getClass().getClassLoader(), interfaces.toArray(new Class<?>[0]),
			new BeanchMarkHandler(bean));
	    }
	}

	return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String arg1) throws BeansException {
	return bean;
    }

}
