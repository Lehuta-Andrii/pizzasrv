package org.study.pizzaservice.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StateConvertor implements AttributeConverter<State, String> {

    @Override
    public String convertToDatabaseColumn(State arg0) {
	return arg0.getState();
    }

    @Override
    public State convertToEntityAttribute(String arg0) {
	return new State(arg0);
    }
    
}
