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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCardImpl;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.service.AccumulativeCardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/H2Repository.xml" })
public class AccumulativeCardServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private AccumulativeCardService cardService;

	@Test
	public void getCardTest() {

		final String sqlCustomer = "INSERT INTO customers (name) VALUES ('Semen')";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlCustomer, new String[] { "id" });
			}
		}, keyHolder);

		Long customerId = keyHolder.getKey().longValue();
		Customer customer = new Customer(customerId, "Semen");

		final String sqlCard = "INSERT INTO accumulativecards (sum, customer_id) VALUES (100, " + customerId + ")";
		jdbcTemplate.update(sqlCard);

		AccumulativeCard card = cardService.getCard(customer).get();

		assertNotNull(card);
		assertEquals(card.getSum(), 100, 0.00001);

	}

	@Test
	public void setNewCardTest() {

		final String sqlCustomer = "INSERT INTO customers (name) VALUES ('Semen')";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlCustomer, new String[] { "id" });
			}
		}, keyHolder);

		Long customerId = keyHolder.getKey().longValue();
		Customer customer = new Customer(customerId, "Semen");

		assertTrue(cardService.setNewCard(customer));

		AccumulativeCard card = jdbcTemplate.queryForObject(
				"select * from accumulativecards where customer_id =" + customerId, new CardRowMapper());

		assertNotNull(card);
		assertEquals(card.getSum(), 0, 0.00001);
	}
	
	@Test
	public void addSumToCardTest() {

		final String sqlCustomer = "INSERT INTO customers (name) VALUES ('Semen')";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlCustomer, new String[] { "id" });
			}
		}, keyHolder);

		Long customerId = keyHolder.getKey().longValue();
		Customer customer = new Customer(customerId, "Semen");

		final String sqlCard = "INSERT INTO accumulativecards (sum, customer_id) VALUES (500, " + customerId + ")";
		jdbcTemplate.update(sqlCard);
		
		assertTrue(cardService.addSumToCard(customer,100));

		AccumulativeCard card = jdbcTemplate.queryForObject(
				"select * from accumulativecards where customer_id =" + customerId, new CardRowMapper());

		assertNotNull(card);
		assertEquals(card.getSum(), 600, 0.00001);
	}
	
	@Test(expected = IncorrectResultSizeDataAccessException.class)
	public void removeCardTest() {

		final String sqlCustomer = "INSERT INTO customers (name) VALUES ('Semen')";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlCustomer, new String[] { "id" });
			}
		}, keyHolder);

		Long customerId = keyHolder.getKey().longValue();
		Customer customer = new Customer(customerId, "Semen");

		final String sqlCard = "INSERT INTO accumulativecards (sum, customer_id) VALUES (500, " + customerId + ")";
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

}
