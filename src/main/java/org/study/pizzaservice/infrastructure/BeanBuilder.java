package org.study.pizzaservice.infrastructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

class BeanBuilder {
    private static final String INIT = "init";

    private final Class<?> clazz;
    private Object bean;
    private Object proxy;
    
    
    private ApplicationContext applicationContext;

    public BeanBuilder(Class<?> clazz, ApplicationContext applicationContext) {
	this.clazz = clazz;
	this.applicationContext = applicationContext;
    }

    public void createBeanProxy() {
	Set<String> methodsWithAnnotation = new HashSet<String>();

	for (Method method : clazz.getMethods()) {
	    if (method.isAnnotationPresent(BeanchMark.class) && method.getAnnotation(BeanchMark.class).active()) {
		methodsWithAnnotation.add(method.getName());
	    }
	}

	if (!methodsWithAnnotation.isEmpty()) {
	    Object proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), clazz.getInterfaces(),
		    new BeanchMarkHandler(methodsWithAnnotation, bean));
	    this.proxy = proxy;
	}

    }

    public Object build() {
	if(proxy == null){
	    return bean;
	}else{
	    return proxy;
	}
    }

    public void callPostConstructMethod()
	    throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	Method[] methods = clazz.getMethods();
	for (Method method : methods) {
	    if (method.getAnnotation(PostConstruct.class) != null) {
		method.invoke(bean);
	    }
	}

    }

    public void createBean()
	    throws InstantiationException, IllegalAccessException, InvocationTargetException, Exception {
	bean = createBean(clazz);
    }

    private Object createBean(Class<?> clazz)
	    throws InstantiationException, IllegalAccessException, Exception, InvocationTargetException {
	Constructor<?> constructor = clazz.getConstructors()[0];

	Object bean = null;

	if (constructor.getParameterCount() == 0) {
	    bean = clazz.newInstance();
	} else {
	    bean = createNewInstanceWithParams(constructor);
	}

	return bean;
    }

    private Object createNewInstanceWithParams(Constructor<?> constructor)
	    throws Exception, InstantiationException, IllegalAccessException, InvocationTargetException {
	Object bean;
	Object[] paramBeans = getParams(constructor);

	bean = constructor.newInstance(paramBeans);
	return bean;
    }

    private Object[] getParams(Constructor<?> constructor) throws Exception {
	Class<?>[] paramTypes = constructor.getParameterTypes();
	Object[] paramBeans = new Object[paramTypes.length];
	for (int i = 0; i < paramTypes.length; i++) {
	    paramBeans[i] = getBeanByType(paramTypes[i]);
	}
	return paramBeans;
    }

    private String getBeanNameByType(Class<?> paramType) {
	String name = paramType.getSimpleName();
	String beanN = changeFirstLetterToLowerCase(name);
	return beanN;
    }

    private String changeFirstLetterToLowerCase(String name) {
	String beanN = Character.toLowerCase(name.charAt(0)) + name.substring(1);
	return beanN;
    }

    private Object getBeanByType(Class<?> paramType) throws Exception {
	String paramName = getBeanNameByType(paramType);
	return applicationContext.getBean(paramName);
    }

    public void callInitMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException,
	    IllegalArgumentException, InvocationTargetException {
	try {
	    Method method = clazz.getMethod(INIT);
	    method.invoke(bean);
	} catch (Exception e) {

	}
    }

}
