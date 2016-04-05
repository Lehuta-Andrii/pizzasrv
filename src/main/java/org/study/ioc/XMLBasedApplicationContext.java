package org.study.ioc;

import java.util.HashMap;
import java.util.Map;

import org.study.pizzaservice.repository.InMemOrderRepository;
import org.study.pizzaservice.repository.InMemPizzaRepository;

public final class XMLBasedApplicationContext implements ApplicationContext {

    private Map<String, Class<?>> beans;
    
    private static XMLBasedApplicationContext instance;
        
    private XMLBasedApplicationContext(){
	beans = new HashMap<String, Class<?>>();
	beans.put("orderRepository", InMemOrderRepository.class);
	beans.put("pizzaRepository", InMemPizzaRepository.class);
    }
    
    public static XMLBasedApplicationContext getInstance(){
	if(instance == null){
	    instance = new XMLBasedApplicationContext();
	}
	return instance;
    }
    
    
    
    @Override
    public Object getBean(String beanName) throws Exception {
	return beans.get(beanName).newInstance();
    }

}
