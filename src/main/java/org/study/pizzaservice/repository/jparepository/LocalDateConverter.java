package org.study.pizzaservice.repository.jparepository;

import java.time.LocalDate;

import javax.persistence.AttributeConverter;


public class LocalDateConverter implements AttributeConverter<LocalDate, String> {

	@Override
	public String convertToDatabaseColumn(LocalDate date) {
		return date.toString();
	}

	@Override
	public LocalDate convertToEntityAttribute(String date) {
		return LocalDate.parse(date);
	}

}
