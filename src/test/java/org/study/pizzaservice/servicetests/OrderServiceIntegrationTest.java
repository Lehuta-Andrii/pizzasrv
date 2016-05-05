package org.study.pizzaservice.servicetests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.domain.order.OrderState;
import org.study.pizzaservice.service.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/H2Repository.xml" })
public class OrderServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private OrderService orderService;

	@Test
	public void getOrderByIdTest() {

		final String sqlCustomer = "INSERT INTO customers (id, name) VALUES (nextval('hibernate_sequence'), 'Semen')";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlCustomer, new String[] { "id" });
			}
		}, keyHolder);
		
		Long customerId = keyHolder.getKey().longValue();
		
		final String sqlAddress = "INSERT INTO addresses (id, city, street, house, flat, phoneNumber) VALUES (nextval('hibernate_sequence'), '1','2','3','4','5')";
		keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlAddress, new String[] { "id" });
			}
		}, keyHolder);

		Long addressId = keyHolder.getKey().longValue();
		

		final String sqlOrder = "INSERT INTO orders (id, date,pizzasammount, state,address_id,customer_id) VALUES (nextval('hibernate_sequence'), '2017-11-11', 10, 'org.study.pizzaservice.domain.order.state.NewState',"
				+ addressId +"," + customerId + ")";
		keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlOrder, new String[] { "id" });
			}
		}, keyHolder);

		Long orderId = keyHolder.getKey().longValue();
		
		List<Pizza> pizzas = generatePizzas();
		
		jdbcTemplate.update("INSERT INTO order_pizzasmap(order_id, pizzas_ammount, pizzasmap_key) VALUES ("+ orderId  +","+ 10 +","+ pizzas.get(0).getId() + ")");
		jdbcTemplate.update("INSERT INTO order_pizzasmap(order_id, pizzas_ammount, pizzasmap_key) VALUES ("+ orderId  +","+ 5 +","+ pizzas.get(1).getId() + ")");
		

		Order order = orderService.gerOrderById(orderId);

		assertTrue(order.getDate().equals(LocalDate.of(2017, 11, 11)));
		assertEquals(order.getPizzasAmount(), 10);
		assertTrue(order.getState().getClass().getName().equals("org.study.pizzaservice.domain.order.state.NewState"));
		assertTrue(order.getCustomer().getId().equals(customerId));
		assertEquals(order.getAddress(), new Address(addressId, "1", "2", "3", "4", "5"));

		assertTrue(order.getAmountOfPizza(pizzas.get(0)) == 10);
		assertTrue(order.getAmountOfPizza(pizzas.get(1)) == 5);
		
		assertTrue(order.getPizzasMap().containsKey(pizzas.get(0)));
		assertTrue(order.getPizzasMap().containsKey(pizzas.get(1)));

	}

	@Test(expected = IncorrectResultSizeDataAccessException.class)
	public void removeOrderTest() {
		final String sqlCustomer = "INSERT INTO customers (id, name) VALUES (nextval('hibernate_sequence'), 'Semen')";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlCustomer, new String[] { "id" });
			}
		}, keyHolder);

		Long customerId = keyHolder.getKey().longValue();

		final String sqlAddress = "INSERT INTO addresses (id, city, street, house, flat, phoneNumber) VALUES (nextval('hibernate_sequence'), '1','2','3','4','5')";
		keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlAddress, new String[] { "id" });
			}
		}, keyHolder);

		Long addressId = keyHolder.getKey().longValue();
		
		final String sqlOrder = "INSERT INTO orders (id, date, pizzasammount, state, address_id, customer_id) VALUES (nextval('hibernate_sequence'), '2017-11-11', 10, 'org.study.pizzaservice.domain.order.state.NewState',"
				+ addressId +"," + customerId + ")";
		keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlOrder, new String[] { "id" });
			}
		}, keyHolder);

		Long orderId = keyHolder.getKey().longValue();
		
		Order order = new Order();
		order.setId(orderId);
		
		assertTrue(orderService.removeOrder(order));
		
		Address dbAddress = jdbcTemplate.queryForObject(
				"select * from addresses where id = " + addressId,
				new AddressRowMapper());
		
		assertEquals(dbAddress, new Address(addressId, "1", "2", "3", "4", "5"));
		
		Customer dbCustomer = jdbcTemplate.queryForObject(
				"select * from customers where id = " + customerId,
				new CustomerRowMapper());

		assertTrue(new Customer(customerId, "Semen").equals(dbCustomer));
	
		assertEquals(jdbcTemplate.queryForObject("select count(*) from order_pizzasmap where order_Id = " + orderId, Integer.class), new Integer(0));
		
		jdbcTemplate.queryForObject("select * from orders where id = " + orderId,
				new OrderRowMapper());
		
	}

	@Test
	public void confirmOrderTest() {

		final String sqlCustomer = "INSERT INTO customers (id, name) VALUES (nextval('hibernate_sequence'), 'Semen')";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlCustomer, new String[] { "id" });
			}
		}, keyHolder);

		Long customerId = keyHolder.getKey().longValue();

		final String sqlOrder = "INSERT INTO orders (id, date, pizzasammount, state, customer_id) VALUES (nextval('hibernate_sequence'), '2017-11-11', 10, 'org.study.pizzaservice.domain.order.state.NewState',"
				+ customerId + ")";
		keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlOrder, new String[] { "id" });
			}
		}, keyHolder);

		Long orderId = keyHolder.getKey().longValue();

		Order order = new Order();
		order.setId(orderId);

		assertTrue(orderService.confirmOrder(order));

		String dbState = jdbcTemplate.queryForObject("select state from orders where id = " + orderId, String.class);
		assertTrue("org.study.pizzaservice.domain.order.state.InProgressState".equals(dbState));
	}

	@Test
	public void cancelOrderTest() {

		final String sqlCustomer = "INSERT INTO customers (id, name) VALUES (nextval('hibernate_sequence'), 'Semen')";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlCustomer, new String[] { "id" });
			}
		}, keyHolder);

		Long customerId = keyHolder.getKey().longValue();

		final String sqlOrder = "INSERT INTO orders (id, date, pizzasammount, state, customer_id) VALUES (nextval('hibernate_sequence'), '2017-11-11', 10, 'org.study.pizzaservice.domain.order.state.NewState',"
				+ customerId + ")";
		keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlOrder, new String[] { "id" });
			}
		}, keyHolder);

		Long orderId = keyHolder.getKey().longValue();

		Order order = new Order();
		order.setId(orderId);

		assertTrue(orderService.cancelOrder(order));

		String dbState = jdbcTemplate.queryForObject("select state from orders where id = " + orderId, String.class);
		assertTrue("org.study.pizzaservice.domain.order.state.CanceledState".equals(dbState));
	}

	@Test
	public void accomplishOrderTest() {

		final String sqlCustomer = "INSERT INTO customers (id, name) VALUES (nextval('hibernate_sequence'), 'Semen')";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlCustomer, new String[] { "id" });
			}
		}, keyHolder);

		Long customerId = keyHolder.getKey().longValue();

		final String sqlOrder = "INSERT INTO orders (id, date, pizzasammount, state, customer_id) VALUES (nextval('hibernate_sequence'), '2017-11-11', 10, 'org.study.pizzaservice.domain.order.state.NewState',"
				+ customerId + ")";
		keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlOrder, new String[] { "id" });
			}
		}, keyHolder);

		Long orderId = keyHolder.getKey().longValue();

		Order order = new Order();
		order.setId(orderId);

		assertTrue(orderService.accomplishOrder(order));

		String dbState = jdbcTemplate.queryForObject("select state from orders where id = " + orderId, String.class);
		assertTrue("org.study.pizzaservice.domain.order.state.DoneState".equals(dbState));
	}

	@Test
	public void placeNewOrderTest() {
		final String sqlCustomer = "INSERT INTO customers (id, name) VALUES (nextval('hibernate_sequence'), 'Semen')";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlCustomer, new String[] { "id" });
			}
		}, keyHolder);

		Long customerId = keyHolder.getKey().longValue();
		Customer customer = new Customer();
		customer.setId(customerId);
		customer.setName("Semen");

		final String sqlAddress = "INSERT INTO addresses (id, city, street, house, flat, phoneNumber) VALUES (nextval('hibernate_sequence'), '1','2','3','4','5')";
		keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlAddress, new String[] { "id" });
			}
		}, keyHolder);

		Long addressId = keyHolder.getKey().longValue();
		Address orderAddress = new Address(addressId, "1", "2", "3", "4", "5");

		List<Long> pizzas = pizzasInsertion();

		Order order = orderService.placeNewOrder(customer, orderAddress, pizzas.toArray(new Long[0]));

		assertNotNull(order);

		Order dbOrder = jdbcTemplate.queryForObject("select * from orders where id = " + order.getId(),
				new OrderRowMapper());

		assertTrue(dbOrder.getId().equals(order.getId()));
		assertTrue(order.getPizzasAmount() == dbOrder.getPizzasAmount());

		Customer dbCustomer = jdbcTemplate.queryForObject(
				"select * from customers where id = (select customer_id from orders where id = " + order.getId() + ")",
				new CustomerRowMapper());

		assertTrue(customer.equals(dbCustomer));

		getPizzasOfOrder(dbOrder);

		assertTrue(dbOrder.getPizzasMap().equals(order.getPizzasMap()));

		Address dbAddres = jdbcTemplate.queryForObject(
				"select * from addresses where id = (select address_id from orders where id =" + order.getId() + " )",
				new AddressRowMapper());

		assertEquals(dbAddres, order.getAddress());
		assertEquals(order.getDate(), LocalDate.now());

	}

	private void getPizzasOfOrder(Order order) {
		Map<Pizza, Integer> dbPizzasMap = jdbcTemplate
				.query("select * from (order_pizzasmap join pizzas on pizzas.id = order_pizzasmap.pizzasmap_key) where order_id ="
						+ order.getId(), new ResultSetExtractor<Map<Pizza, Integer>>() {
							@Override
							public Map<Pizza, Integer> extractData(ResultSet rs)
									throws SQLException, DataAccessException {
								Map<Pizza, Integer> mapRet = new HashMap<Pizza, Integer>();
								while (rs.next()) {
									mapRet.put(
											new Pizza(rs.getLong("id"), rs.getString("name"), rs.getDouble("price"),
													Pizza.Type.valueOf(rs.getString("pizzatype").toUpperCase())),
											rs.getInt("pizzas_ammount"));
								}
								return mapRet;
							}

						});

		Class<?> orderClass = order.getOrderContext().getClass();
		try {
			Field ammount = orderClass.getDeclaredField("pizzasMap");
			ammount.setAccessible(true);
			ammount.set(order.getOrderContext(), dbPizzasMap);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static final class OrderRowMapper implements RowMapper<Order> {

		public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

			Order result = new Order();
			result.setId(rs.getLong("id"));
			result.setDate(LocalDate.parse(rs.getString("date")));
			try {
				result.setState((OrderState) Class.forName(rs.getString("state")).newInstance());
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}

			Class<?> orderClass = result.getOrderContext().getClass();
			try {
				Field ammount = orderClass.getDeclaredField("pizzasAmmount");
				ammount.setAccessible(true);
				ammount.set(result.getOrderContext(), rs.getInt("pizzasammount"));
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

			return result;
		}
	}

	private static final class CustomerRowMapper implements RowMapper<Customer> {
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer result = new Customer(rs.getLong("id"), rs.getString("name"));
			return result;
		}
	}

	private static final class AddressRowMapper implements RowMapper<Address> {
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
			Address result = new Address(rs.getLong("id"), rs.getString("city"), rs.getString("street"),
					rs.getString("house"), rs.getString("flat"), rs.getString("phoneNumber"));
			return result;
		}
	}

	private List<Pizza> generatePizzas() {

		List<Pizza> result = new ArrayList<Pizza>();

		final String firstPizza = "INSERT INTO pizzas (id, name, pizzaType, price) VALUES (nextval('hibernate_sequence'), 'PizzaOne', 'MEAT', 130.0)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(firstPizza, new String[] { "id" });
			}
		}, keyHolder);

		result.add(new Pizza(keyHolder.getKey().longValue(), "PizzaOne", 130, Pizza.Type.MEAT));

		final String secondPizza = "INSERT INTO pizzas (id, name, pizzaType, price) VALUES (nextval('hibernate_sequence'), 'PizzaTwo', 'MEAT', 10.0)";
		keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(secondPizza, new String[] { "id" });
			}
		}, keyHolder);

		result.add(new Pizza(keyHolder.getKey().longValue(), "PizzaTwo", 10, Pizza.Type.MEAT));

		return result;
	}

	
	private List<Long> pizzasInsertion() {

		List<Long> result = new ArrayList<Long>();

		final String firstPizza = "INSERT INTO pizzas (id, name, pizzaType, price) VALUES (nextval('hibernate_sequence'), 'PizzaOne', 'MEAT', 130.0)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(firstPizza, new String[] { "id" });
			}
		}, keyHolder);

		result.add(keyHolder.getKey().longValue());
		result.add(keyHolder.getKey().longValue());
		result.add(keyHolder.getKey().longValue());

		final String secondPizza = "INSERT INTO pizzas (id, name, pizzaType, price) VALUES (nextval('hibernate_sequence'), 'PizzaTwo', 'MEAT', 10.0)";
		keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(secondPizza, new String[] { "id" });
			}
		}, keyHolder);

		result.add(keyHolder.getKey().longValue());
		result.add(keyHolder.getKey().longValue());

		return result;
	}

}
