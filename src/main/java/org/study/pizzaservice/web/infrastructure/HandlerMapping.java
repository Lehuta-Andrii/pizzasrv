package org.study.pizzaservice.web.infrastructure;

public interface HandlerMapping {
    Controller getController(String url);
}
