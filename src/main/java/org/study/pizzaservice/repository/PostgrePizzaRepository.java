package org.study.pizzaservice.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.study.pizzaservice.domain.Pizza;

@Repository
public class PostgrePizzaRepository implements PizzaRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgrePizzaRepository(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public List<Pizza> getPizzas() {
	return jdbcTemplate.query("select * from pizzas", new Object[0], new PizzaRowMapper());
    }

    @Override
    public boolean setPizzas(List<Pizza> pizzas) {
	return false;
    }

    @Override
    public boolean addPizza(Pizza pizza) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	int result = jdbcTemplate.update(new PreparedStatementCreator() {
	    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(
			"insert into pizzas (name, price, pizzaType) values (?,?,?::pizzaType)", new String[] { "id" });
		ps.setString(1, pizza.getName());
		ps.setDouble(2, pizza.getPrice());
		ps.setString(3, pizza.getPizzaType().toString().toLowerCase());

		return ps;
	    }

	}, keyHolder);

	if (result != 0) {
	    pizza.setId(keyHolder.getKey().intValue());

	    return true;
	}

	return false;
    }

    @Override
    public boolean deletePizza(Pizza pizza) {
	if (jdbcTemplate.update("delete from pizzas where id = ?", pizza.getId()) != 0) {
	    return true;
	}
	return false;

    }

    @Override
    public boolean updatePizza(Pizza pizza) {
	if (jdbcTemplate.update("update pizzas set name = ?, price = ?, pizzaType = ?::pizzaType where id = ?",
		pizza.getName(), pizza.getPrice(), pizza.getPizzaType().toString().toLowerCase(), pizza.getId()) != 0) {
	    return true;
	}
	return false;
    }

    @Override
    public Optional<Pizza> getPizzaByID(Integer id) {
	try {
	    return Optional.<Pizza> of(jdbcTemplate.queryForObject("select * from pizzas where id = ?",
		    new Object[] { id }, new PizzaRowMapper()));

	} catch (Exception e) {
	    return Optional.empty();
	}

    }

    private static final class PizzaRowMapper implements RowMapper<Pizza> {
	public Pizza mapRow(ResultSet rs, int rowNum) throws SQLException {
	    Pizza result = new Pizza(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"),
		    Pizza.Type.valueOf(rs.getString("pizzatype").toUpperCase()));

	    return result;
	}
    }

}
