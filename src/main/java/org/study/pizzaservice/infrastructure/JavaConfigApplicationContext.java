package org.study.pizzaservice.infrastructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class JavaConfigApplicationContext implements ApplicationContext {

    private final Config config = new JavaConfig();

    private final Map<String, Object> context = new HashMap<String, Object>();

    @Override
    public Object getBean(String beanName) throws Exception {

	if (context.containsKey(beanName)) {
	    return context.get(beanName);
	}

	Class<?> clazz = config.getImpl(beanName);
	if (clazz == null) {
	    throw new RuntimeException("Bean not found");
	}

	Constructor<?> constructor = clazz.getConstructors()[0];
	Class<?>[] paramTypes = constructor.getParameterTypes();

	Object bean = null;

	if (paramTypes.length == 0) {
	    bean = clazz.newInstance();
	} else {

	    Object[] paramBeans = new Object[paramTypes.length];
	    for(int i=0;i<paramTypes.length;i++){
		String name = paramTypes[i].getSimpleName();
		String beanN = Character.toLowerCase(name.charAt(0)) + name.substring(1);
		paramBeans[i] = getBean(beanN);
	    }
		
	bean = constructor.newInstance(paramBeans);
	}

	context.put(beanName, bean);
	return bean;

    }

}
