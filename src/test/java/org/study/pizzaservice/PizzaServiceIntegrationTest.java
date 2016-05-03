package org.study.pizzaservice;

import java.sql.*;
import java.util.List;

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
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.service.PizzasService;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/H2Repository.xml" })
public class PizzaServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    PizzasService pizzasService;

    @Test
    public void testGetPizzaByID() {
	final String sql = "INSERT INTO pizzas (name, pizzaType, price) VALUES ('Vegan', " + "'MEAT', 130.0)";
	KeyHolder keyHolder = new GeneratedKeyHolder();

	jdbcTemplate.update(new PreparedStatementCreator() {

	    @Override
	    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		return con.prepareStatement(sql, new String[] { "id" });
	    }
	}, keyHolder);

	Long id = keyHolder.getKey().longValue();

	Pizza pizza = pizzasService.getPizzaById(id);
	assertNotNull(pizza);
	assertEquals(pizza.getPrice(), 130.0, 0.00001);
	assertEquals(pizza.getPizzaType(), Pizza.Type.MEAT);
	assertEquals(pizza.getName(), "Vegan");

    }
    
    @Test
    public void testGetPizzas() {
	final String pizzaOne = "INSERT INTO pizzas (name, pizzaType, price) VALUES ('OnePizza', " + "'MEAT', 130.0)";
	final String pizzaTwo = "INSERT INTO pizzas (name, pizzaType, price) VALUES ('TwoPizza', " + "'SEA', 150.0)";
	
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(new PreparedStatementCreator() {

	    @Override
	    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		return con.prepareStatement(pizzaOne, new String[] { "id" });
	    }
	}, keyHolder);

	Long id = keyHolder.getKey().longValue();

	Pizza firstPizza = new Pizza(id, "OnePizza", 130.0, Pizza.Type.MEAT);
	
	keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(new PreparedStatementCreator() {

	    @Override
	    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		return con.prepareStatement(pizzaTwo, new String[] { "id" });
	    }
	}, keyHolder);

	id = keyHolder.getKey().longValue();

	Pizza secondPizza = new Pizza(id, "TwoPizza", 150.0, Pizza.Type.SEA);

	List<Pizza> dbPizzas = pizzasService.getPizzas();
	
	
	assertTrue(dbPizzas.contains(secondPizza));
	assertTrue(dbPizzas.contains(firstPizza));

    }

    

    @Test(expected = IncorrectResultSizeDataAccessException.class)
    public void testDeletePizza() {

	final String sql = "INSERT INTO pizzas (name, pizzaType, price) VALUES ('OtherPizza', " + "'SEA', 130.0)";

	KeyHolder keyHolder = new GeneratedKeyHolder();

	jdbcTemplate.update(new PreparedStatementCreator() {

	    @Override
	    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		return con.prepareStatement(sql, new String[] { "id" });
	    }
	}, keyHolder);

	Long id = keyHolder.getKey().longValue();

	Pizza pizza = new Pizza(id, "OtherPizza", 130.0, Pizza.Type.SEA);

	assertTrue(pizzasService.deletePizza(pizza));

	
	Pizza inDbPizza = jdbcTemplate.queryForObject("select * from pizzas where id = ?",
		new Object[] { pizza.getId() }, new PizzaRowMapper());

	

    }

    @Test
    public void testAddPizza() {

	Pizza pizza = new Pizza("Pizza", 159, Pizza.Type.SEA);

	assertTrue(pizzasService.addPizza(pizza));

	Pizza inDbPizza = jdbcTemplate.queryForObject("select * from pizzas where id = ?",
		new Object[] { pizza.getId() }, new PizzaRowMapper());

	assertNotNull(inDbPizza);
	assertEquals(pizza.getPrice(), inDbPizza.getPrice(), 0.00001);
	assertEquals(pizza.getPizzaType(), inDbPizza.getPizzaType());
	assertEquals(pizza.getName(), inDbPizza.getName());

    }

    private static final class PizzaRowMapper implements RowMapper<Pizza> {
	public Pizza mapRow(ResultSet rs, int rowNum) throws SQLException {
	    Pizza result = new Pizza(rs.getLong("id"), rs.getString("name"), rs.getDouble("price"),
		    Pizza.Type.valueOf(rs.getString("pizzatype").toUpperCase()));

	    return result;
	}
    }

}
