package org.study.pizzaservice.infrastructure;

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

	//Object bean = createBean(clazz);
	Object bean;
	
	BeanBuilder builder = new BeanBuilder(clazz, this);
	builder.createBean();
	builder.createBeanProxy();
	/*
	 * @BeanchMark on method, active = true/false, default = true
	 * cjlib
	 * java plain proxy 
	 * Proxy.newPorxyInstance(null,interface, null);
	 */
	builder.callPostConstructMethod();
	builder.callInitMethod();
	bean = builder.build();
	
	context.put(beanName, bean);
	return bean;

    }
    
  }
