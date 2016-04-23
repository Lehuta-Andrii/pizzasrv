package org.study.pizzaservice.repository.jparepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.repository.AccumulativeCardRepository;

@Repository
@Transactional
public class JpaAccumulativeCardRepository implements AccumulativeCardRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Optional<AccumulativeCard> getCard(Customer customer) {
	return null;
    }

    @Override
    public boolean addCard(AccumulativeCard card) {
	entityManager.persist(card);
	return true;
    }

    @Override
    public List<AccumulativeCard> getCards() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean removeCustomerCard(Customer customer) {
	// TODO Auto-generated method stub
	return false;
    }

}
