package org.study.pizzaservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.study.pizzaservice.domain.Pizza;

@Repository
public class PostgrePizzaRepository implements PizzaRepository {

	private JdbcOperations jdbcOperations;

	@Autowired
	public PostgrePizzaRepository(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	public List<Pizza> getPizzas() {
		// jdbcOperations.queryForList("select * from pizzas", Pizza.class);
		return null;
	}

	@Override
	public boolean setPizzas(List<Pizza> pizzas) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPizza(Pizza pizza) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePizza(Pizza pizza) {
		try{
			
		} catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean updatePizza(Pizza pizza) {
		return false;
	}

	@Override
	public Optional<Pizza> getPizzaByID(Integer id) {
		try {
			return Optional.<Pizza>of(jdbcOperations.queryForObject("select * from pizzas where id = ?",
					new Object[] { id }, new PizzaRowMapper()));

		} catch (Exception e) {
			return Optional.empty();
		}

	}

	private static final class PizzaRowMapper implements RowMapper<Pizza> {
		public Pizza mapRow(ResultSet rs, int rowNum) throws SQLException {
			Pizza result = new Pizza(rs.getString("name"), rs.getDouble("price"),
					Pizza.Type.valueOf(rs.getString("pizzatype").toUpperCase()));

			result.setId(rs.getInt("id"));
			return result;
		}
	}

}
