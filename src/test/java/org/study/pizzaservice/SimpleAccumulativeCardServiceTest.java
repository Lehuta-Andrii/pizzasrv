package org.study.pizzaservice;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.repository.inmemrepository.InMemAccumulativeCardRepository;
import org.study.pizzaservice.service.simpleservice.SimpleAccumulativeCardService;

import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SimpleAccumulativeCardServiceTest {

    @Mock
    private InMemAccumulativeCardRepository mockCardRepository;
    
    private SimpleAccumulativeCardService cardService;
    
    @Before
    public void setUp(){
	cardService = new SimpleAccumulativeCardService(mockCardRepository);
    }
    
    @Test
    public void addSumToCardWithNoCardTest() {
	
	when(mockCardRepository.getCard(null)).thenReturn(Optional.<AccumulativeCard>empty());
	
	assertFalse(cardService.addSumToCard(null, 0));
    }
    
    @Test
    public void addSumToCardWithCardTest() {

	AccumulativeCard mockCard = mock(AccumulativeCard.class);
	
	when(mockCardRepository.getCard(null)).thenReturn(Optional.<AccumulativeCard>of(mockCard));
	
	assertTrue(cardService.addSumToCard(null, 0));
    }

}
