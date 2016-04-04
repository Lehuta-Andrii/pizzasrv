package org.study.pizzaservice.infrastructure;

public class ServiceLocator {

    private final Config config = new JavaConfig();

    private final static ServiceLocator instance = new ServiceLocator();

    public static ServiceLocator getInstance() {
	return instance;
    }

    public Object lookup(String bean) {
	Class<?> clazz = config.getImpl(bean);
	if (clazz == null) {
	    throw new RuntimeException("Bean not found");
	}

	 try {
	    return clazz.newInstance();
	} catch (InstantiationException | IllegalAccessException e) {
	    e.printStackTrace();
	    throw new RuntimeException("Bean not found");

	}

    }

    private ServiceLocator() {

    }
}
