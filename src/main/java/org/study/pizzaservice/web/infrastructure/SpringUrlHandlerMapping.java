package org.study.pizzaservice.web.infrastructure;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.study.pizzaservice.web.GetPizzaController;

public class SpringUrlHandlerMapping implements HandlerMapping {

    private Map<String, Controller> mapping = new HashMap<>();
    private ApplicationContext webContext;
    
    public SpringUrlHandlerMapping(ApplicationContext context){
	this.webContext = context;
	mapping.put("/hello", webContext.getBean(GetPizzaController.class));
    }
    
    
    @Override
    public Controller getController(String url) {
	return mapping.get(url);
    }

}
