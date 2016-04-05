package org.study.pizzaservice.repository;

import java.util.ArrayList;
import java.util.List;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;

public class InMemAccumulativeCardRepository implements AccumulativeCardRepository {
	List<AccumulativeCard> cards = new ArrayList<AccumulativeCard>();

	public InMemAccumulativeCardRepository() {
		this.cards.add(new AccumulativeCard());
		this.cards.add(new AccumulativeCard());
		this.cards.add(new AccumulativeCard());
	}

	@Override
	public AccumulativeCard getCardById(Integer id) {
		for (AccumulativeCard card : cards) {
			if (id.equals(card.getId())) {
				return card;
			}
		}
		return null;
	}

}
