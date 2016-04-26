package org.study.pizzaservice.repository.jparepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCardImpl;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.repository.AccumulativeCardRepository;

@Repository
@Transactional
public class JpaAccumulativeCardRepository implements AccumulativeCardRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<AccumulativeCard> getCard(Customer customer) {
		TypedQuery<AccumulativeCard> query = entityManager
				.createQuery("SELECT a FROM AccumulativeCardImpl a WHERE a.customer = :cust", AccumulativeCard.class)
				.setParameter("cust", customer);
		return Optional.of(query.getSingleResult());
	}

	@Override
	public boolean addCard(AccumulativeCard card) {
		try {
			entityManager.persist(card);
		} catch (PersistenceException ex) {
			System.err.println(ex);
			return false;
		}
		return true;
	}
	
	@Override
	public List<AccumulativeCard> getCards() {
		TypedQuery<AccumulativeCard> query = entityManager.createQuery("SELECT a FROM AccumulativeCardImpl a",
				AccumulativeCard.class);
		return query.getResultList();
	}

	@Override
	public boolean removeCustomerCard(Customer customer) {
		Query query = entityManager
				.createQuery("DELETE FROM AccumulativeCardImpl a WHERE a.customer = :cust")
				.setParameter("cust", customer);
		return query.executeUpdate() > 0;
	}

	@Override
	public boolean updateCard(AccumulativeCard card) {
		AccumulativeCard dbCard = entityManager.find(AccumulativeCardImpl.class, card.getId());
		dbCard.setCustomer(card.getCustomer());
		dbCard.setSum(card.getSum());
		return true;
	}

}
