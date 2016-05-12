package org.study.pizzaservice.servicetests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCardImpl;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.service.AccumulativeCardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/H2Repository.xml" })
public class AccumulativeCardServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private AccumulativeCardService cardService;
	
	private DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	
	@Autowired
	private JpaTransactionManager  transactionManager;

	@Test
	public void getCardTest() {

		String customerName = "Semen";

		KeyHolder keyHolder = insertCustomer(customerName);

		Long customerId = keyHolder.getKey().longValue();
		Customer customer = new Customer(customerId, customerName);

		final String sqlCard = "INSERT INTO accumulativecards (id, sum, customer_id) VALUES (nextval('hibernate_sequence'), 100, "
				+ customerId + ")";
		jdbcTemplate.update(sqlCard);

		AccumulativeCard card = cardService.getCard(customer).get();

		assertNotNull(card);
		assertEquals(card.getSum(), 100, 0.00001);

	}

	@Test
	public void setNewCardTest() {

		String customerName = "Semen";

		KeyHolder keyHolder = insertCustomer(customerName);

		Long customerId = keyHolder.getKey().longValue();
		Customer customer = new Customer(customerId, customerName);

		assertTrue(cardService.setNewCard(customer));
		
		transactionManager.getTransaction(def).flush();

		AccumulativeCard card = jdbcTemplate.queryForObject(
				"select * from accumulativecards where customer_id =" + customerId, new CardRowMapper());

		assertNotNull(card);
		assertEquals(card.getSum(), 0, 0.00001);
	}

	@Test
	public void addSumToCardTest() {

		String customerName = "Semen";

		KeyHolder keyHolder = insertCustomer(customerName);

		Long customerId = keyHolder.getKey().longValue();
		Customer customer = new Customer(customerId, customerName);

		final String sqlCard = "INSERT INTO accumulativecards (id, sum, customer_id) VALUES (nextval('hibernate_sequence'), 500, "
				+ customerId + ")";
		jdbcTemplate.update(sqlCard);

		assertTrue(cardService.addSumToCard(customer, 100));

		transactionManager.getTransaction(def).flush();
		
		AccumulativeCard card = jdbcTemplate.queryForObject(
				"select * from accumulativecards where customer_id =" + customerId, new CardRowMapper());

		assertNotNull(card);
		assertEquals(card.getSum(), 600, 0.00001);
	}

	@Test(expected = IncorrectResultSizeDataAccessException.class)
	public void removeCardTest() {

		String customerName = "Semen";

		KeyHolder keyHolder = insertCustomer(customerName);

		Long customerId = keyHolder.getKey().longValue();
		Customer customer = new Customer(customerId, customerName);

		final String sqlCard = "INSERT INTO accumulativecards (id, sum, customer_id) VALUES (nextval('hibernate_sequence'), 500, "
				+ customerId + ")";
		jdbcTemplate.update(sqlCard);

		assertTrue(cardService.removeCard(customer));

		AccumulativeCard card = jdbcTemplate.queryForObject(
				"select * from accumulativecards where customer_id =" + customerId, new CardRowMapper());

		assertNotNull(card);
	}

	private static final class CardRowMapper implements RowMapper<AccumulativeCard> {
		public AccumulativeCard mapRow(ResultSet rs, int rowNum) throws SQLException {
			AccumulativeCard result = new AccumulativeCardImpl();
			result.setSum(rs.getLong("sum"));
			result.setId(rs.getLong("id"));

			return result;
		}
	}

	private KeyHolder insertCustomer(String customerName) {
		final String sqlCustomer = "INSERT INTO customers (id, name) VALUES (nextval('hibernate_sequence'), '"
				+ customerName + "')";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlCustomer, new String[] { "id" });
			}
		}, keyHolder);
		return keyHolder;
	}

}
