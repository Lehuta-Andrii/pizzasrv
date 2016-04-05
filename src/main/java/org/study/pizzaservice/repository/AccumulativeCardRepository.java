package org.study.pizzaservice.repository;

import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;

public interface AccumulativeCardRepository {
	AccumulativeCard getCardById(Integer id);
}
