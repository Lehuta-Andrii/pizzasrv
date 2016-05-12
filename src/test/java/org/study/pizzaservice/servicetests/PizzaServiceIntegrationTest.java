package org.study.pizzaservice.servicetests;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.service.PizzasService;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/H2Repository.xml" })
public class PizzaServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PizzasService pizzasService;
	
	private DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	
	@Autowired
	private JpaTransactionManager  transactionManager;
	
	@Test
	public void testGetPizzaByID() {

		double excpectedPrice = 130.0;
		Pizza.Type excpectedType = Pizza.Type.MEAT;
		String pizzaName = "Vegan";

		KeyHolder keyHolder = insertPizza(excpectedPrice, excpectedType, pizzaName);

		Long id = keyHolder.getKey().longValue();

		Pizza pizza = pizzasService.getPizzaById(id);
		assertNotNull(pizza);
		assertEquals(pizza.getPrice(), excpectedPrice, 0.00001);
		assertEquals(pizza.getPizzaType(), excpectedType);
		assertEquals(pizza.getName(), pizzaName);

	}

	@Test
	public void testGetPizzas() {

		double excpectedFirstPizzaPrice = 130.0;
		Pizza.Type excpectedFirstPizzaType = Pizza.Type.MEAT;
		String pizzaFirstPizzaName = "OnePizza";

		KeyHolder keyHolder = insertPizza(excpectedFirstPizzaPrice, excpectedFirstPizzaType, pizzaFirstPizzaName);

		Pizza firstPizza = new Pizza(keyHolder.getKey().longValue(), pizzaFirstPizzaName, excpectedFirstPizzaPrice,
				excpectedFirstPizzaType);

		double excpectedSecondPizzaPrice = 130.0;
		Pizza.Type excpectedSecondPizzaType = Pizza.Type.SEA;
		String pizzaSecondPizzaName = "TwoPizza";

		keyHolder = insertPizza(excpectedSecondPizzaPrice, excpectedSecondPizzaType, pizzaSecondPizzaName);

		Pizza secondPizza = new Pizza(keyHolder.getKey().longValue(), pizzaSecondPizzaName, excpectedSecondPizzaPrice,
				excpectedSecondPizzaType);

		List<Pizza> dbPizzas = pizzasService.getPizzas();

		assertTrue(dbPizzas.contains(secondPizza));
		assertTrue(dbPizzas.contains(firstPizza));

	}

	@Test(expected = IncorrectResultSizeDataAccessException.class)
	public void testDeletePizza() {

		double excpectedPrice = 130.0;
		Pizza.Type excpectedType = Pizza.Type.SEA;
		String pizzaName = "OtherPizza";

		KeyHolder keyHolder = insertPizza(excpectedPrice, excpectedType, pizzaName);

		Long id = keyHolder.getKey().longValue();

		Pizza pizza = new Pizza(id, pizzaName, excpectedPrice, excpectedType);

		assertTrue(pizzasService.deletePizza(pizza));
		
		transactionManager.getTransaction(def).flush();

		jdbcTemplate.queryForObject("select * from pizzas where id = ?", new Object[] { pizza.getId() },
				new PizzaRowMapper());

	}

	@Test
	public void testAddPizza() {

		Pizza pizza = new Pizza("Pizza", 159, Pizza.Type.SEA);
		assertTrue(pizzasService.addPizza(pizza));

		transactionManager.getTransaction(def).flush();

		Pizza inDbPizza = jdbcTemplate.queryForObject("select * from pizzas where id = ?",
				new Object[] { pizza.getId() }, new PizzaRowMapper());

		assertNotNull(inDbPizza);
		assertEquals(pizza.getPrice(), inDbPizza.getPrice(), 0.00001);
		assertEquals(pizza.getPizzaType(), inDbPizza.getPizzaType());
		assertEquals(pizza.getName(), inDbPizza.getName());

	}
	

	@Test
	public void testSetPizzas() {

		Pizza firstPizza = new Pizza("OneSetPizza", 130.0, Pizza.Type.MEAT);
		Pizza secondPizza = new Pizza("TwoSetPizza", 150.0, Pizza.Type.SEA);

		assertTrue(pizzasService.setPizzas(new ArrayList<Pizza>() {
			private static final long serialVersionUID = 1L;

			{
				add(firstPizza);
				add(secondPizza);
			}
		}));

		Pizza inDbFirstPizza = jdbcTemplate.queryForObject("select * from pizzas where id = ?",
				new Object[] { firstPizza.getId() }, new PizzaRowMapper());

		assertTrue(inDbFirstPizza.equals(firstPizza));

		Pizza inDbSecondPizza = jdbcTemplate.queryForObject("select * from pizzas where id = ?",
				new Object[] { secondPizza.getId() }, new PizzaRowMapper());

		assertTrue(inDbSecondPizza.equals(secondPizza));

	}

	private static final class PizzaRowMapper implements RowMapper<Pizza> {
		public Pizza mapRow(ResultSet rs, int rowNum) throws SQLException {
			Pizza result = new Pizza(rs.getLong("id"), rs.getString("name"), rs.getDouble("price"),
					Pizza.Type.valueOf(rs.getString("pizzatype").toUpperCase()));

			return result;
		}
	}

	private KeyHolder insertPizza(double excpectedPrice, Pizza.Type excpectedType, String pizzaName) {
		final String sql = "INSERT INTO pizzas (id, name, pizzaType, price) VALUES (nextval('hibernate_sequence'), '"
				+ pizzaName + "', '" + excpectedType + "', " + excpectedPrice + ")";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sql, new String[] { "id" });
			}
		}, keyHolder);
		return keyHolder;
	}

}
