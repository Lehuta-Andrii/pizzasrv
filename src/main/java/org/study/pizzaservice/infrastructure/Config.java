package org.study.pizzaservice.infrastructure;

public interface Config {
    Class<?> getImpl(String bean);
}
