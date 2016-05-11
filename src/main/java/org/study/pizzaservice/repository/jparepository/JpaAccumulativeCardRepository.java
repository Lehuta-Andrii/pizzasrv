package org.study.pizzaservice.repository.jparepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.*;

import org.springframework.stereotype.Repository;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCardImpl;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.repository.AccumulativeCardRepository;

@Repository
public class JpaAccumulativeCardRepository implements AccumulativeCardRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<AccumulativeCard> getCard(Customer customer) {
		TypedQuery<AccumulativeCard> query = entityManager
				.createNamedQuery("AccumCard.getCardByCustomer", AccumulativeCard.class)
				.setParameter("cust", customer);
		return Optional.of(query.getSingleResult());
	}

	@Override
	public boolean addCard(AccumulativeCard card) {
		try {
			entityManager.persist(card);
			entityManager.flush();
		} catch (PersistenceException ex) {
			System.err.println(ex);
			return false;
		}
		return true;
	}
	
	@Override
	public List<AccumulativeCard> getCards() {
		TypedQuery<AccumulativeCard> query = entityManager.createNamedQuery("AccumCard.getCards",
				AccumulativeCard.class);
		return query.getResultList();
	}

	@Override
	public boolean removeCustomerCard(Customer customer) {
		Query query = entityManager
				.createNamedQuery("AccumCard.deleteCardByCustomer")
				.setParameter("cust", customer);
		return query.executeUpdate() > 0;
	}

	@Override
	public boolean updateCard(AccumulativeCard card) {
		AccumulativeCard dbCard = entityManager.find(AccumulativeCardImpl.class, card.getId());
		if(dbCard != null){
			
			entityManager.merge(card);
			entityManager.flush();
			return true;
			
		}
		
		return false;
	}

}
