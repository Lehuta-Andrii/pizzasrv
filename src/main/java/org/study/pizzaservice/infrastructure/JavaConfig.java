package org.study.pizzaservice.infrastructure;

import java.util.HashMap;
import java.util.Map;

import org.study.pizzaservice.SimpleOrderService;
import org.study.pizzaservice.repository.InMemOrderRepository;
import org.study.pizzaservice.repository.InMemPizzaRepository;

public class JavaConfig implements Config{

    private Map<String, Class<?>> beans = new HashMap<>();
    
    {
	beans.put("orderRepository", InMemOrderRepository.class);
	beans.put("pizzaRepository", InMemPizzaRepository.class);
	beans.put("orderService", SimpleOrderService.class);
    }
    
    @Override
    public Class<?> getImpl(String bean) {
	return beans.get(bean);
    }

}
