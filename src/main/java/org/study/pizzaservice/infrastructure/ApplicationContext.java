package org.study.pizzaservice.infrastructure;

public interface ApplicationContext {
    public Object getBean(String bean) throws Exception;
}
