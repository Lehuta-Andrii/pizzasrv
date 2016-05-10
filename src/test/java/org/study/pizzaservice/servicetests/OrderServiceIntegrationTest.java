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

		String customerName = "Semen";
		KeyHolder keyHolder = insertCustomer(customerName);
		Long customerId = keyHolder.getKey().longValue();

		keyHolder = insertAddress("1", "2", "3", "4", "5");
		Long addressId = keyHolder.getKey().longValue();

		String orderDate = "2017-11-11";
		int pizzaAmmount = 10;
		keyHolder = insertOrder(customerId, addressId, orderDate, pizzaAmmount);

		Long orderId = keyHolder.getKey().longValue();

		List<Pizza> pizzas = new ArrayList<Pizza>();
		pizzas.add(insertPizza(10.0, Pizza.Type.MEAT, "PizzaOne"));
		pizzas.add(insertPizza(10.0, Pizza.Type.MEAT, "PizzaTwo"));

		jdbcTemplate.update("INSERT INTO order_pizzasmap(order_id, pizzas_ammount, pizzasmap_key) VALUES (" + orderId
				+ "," + 6 + "," + pizzas.get(0).getId() + ")");
		jdbcTemplate.update("INSERT INTO order_pizzasmap(order_id, pizzas_ammount, pizzasmap_key) VALUES (" + orderId
				+ "," + 4 + "," + pizzas.get(1).getId() + ")");

		Order order = orderService.gerOrderById(orderId);

		assertTrue(order.getDate().equals(LocalDate.of(2017, 11, 11)));
		assertEquals(order.getPizzasAmount(), pizzaAmmount);
		assertTrue(order.getState().getClass().getName().equals("org.study.pizzaservice.domain.order.state.NewState"));
		assertTrue(order.getCustomer().getId().equals(customerId));
		assertEquals(order.getAddress(), new Address(addressId, "1", "2", "3", "4", "5"));

		assertTrue(order.getAmountOfPizza(pizzas.get(0)) == 6);
		assertTrue(order.getAmountOfPizza(pizzas.get(1)) == 4);

		assertTrue(order.getPizzasMap().containsKey(pizzas.get(0)));
		assertTrue(order.getPizzasMap().containsKey(pizzas.get(1)));

	}

	@Test(expected = IncorrectResultSizeDataAccessException.class)
	public void removeOrderTest() {

		String customerName = "Semen";
		KeyHolder keyHolder = insertCustomer(customerName);
		Long customerId = keyHolder.getKey().longValue();

		keyHolder = insertAddress("1", "2", "3", "4", "5");
		Long addressId = keyHolder.getKey().longValue();

		String orderDate = "2017-11-11";
		int pizzaAmmount = 10;
		keyHolder = insertOrder(customerId, addressId, orderDate, pizzaAmmount);
		Long orderId = keyHolder.getKey().longValue();

		Order order = new Order();
		order.setId(orderId);

		assertTrue(orderService.removeOrder(order));

		Address dbAddress = jdbcTemplate.queryForObject("select * from addresses where id = " + addressId,
				new AddressRowMapper());

		assertEquals(dbAddress, new Address(addressId, "1", "2", "3", "4", "5"));

		Customer dbCustomer = jdbcTemplate.queryForObject("select * from customers where id = " + customerId,
				new CustomerRowMapper());

		assertTrue(new Customer(customerId, customerName).equals(dbCustomer));

		assertEquals(jdbcTemplate.queryForObject("select count(*) from order_pizzasmap where order_Id = " + orderId,
				Integer.class), new Integer(0));

		jdbcTemplate.queryForObject("select * from orders where id = " + orderId, new OrderRowMapper());

	}

	@Test
	public void confirmOrderTest() {

		String expectedState = "org.study.pizzaservice.domain.order.state.InProgressState";

		String customerName = "Semen";
		KeyHolder keyHolder = insertCustomer(customerName);
		Long customerId = keyHolder.getKey().longValue();

		String orderDate = "2017-11-11";
		int pizzaAmmount = 10;
		keyHolder = insertOrder(customerId, null, orderDate, pizzaAmmount);
		Long orderId = keyHolder.getKey().longValue();

		Order order = new Order();
		order.setId(orderId);

		assertTrue(orderService.confirmOrder(order));

		String dbState = jdbcTemplate.queryForObject("select state from orders where id = " + orderId, String.class);
		assertTrue(expectedState.equals(dbState));
	}

	@Test
	public void cancelOrderTest() {

		String expectedState = "org.study.pizzaservice.domain.order.state.CanceledState";

		String customerName = "Semen";
		KeyHolder keyHolder = insertCustomer(customerName);
		Long customerId = keyHolder.getKey().longValue();

		String orderDate = "2017-11-11";
		int pizzaAmmount = 10;
		keyHolder = insertOrder(customerId, null, orderDate, pizzaAmmount);
		Long orderId = keyHolder.getKey().longValue();

		Order order = new Order();
		order.setId(orderId);

		assertTrue(orderService.cancelOrder(order));

		String dbState = jdbcTemplate.queryForObject("select state from orders where id = " + orderId, String.class);
		assertTrue(expectedState.equals(dbState));
	}

	@Test
	public void accomplishOrderTest() {

		String expectedState = "org.study.pizzaservice.domain.order.state.DoneState";

		String customerName = "Semen";
		KeyHolder keyHolder = insertCustomer(customerName);
		Long customerId = keyHolder.getKey().longValue();

		String orderDate = "2017-11-11";
		int pizzaAmmount = 10;
		keyHolder = insertOrder(customerId, null, orderDate, pizzaAmmount);
		Long orderId = keyHolder.getKey().longValue();

		Order order = new Order();
		order.setId(orderId);

		assertTrue(orderService.accomplishOrder(order));

		String dbState = jdbcTemplate.queryForObject("select state from orders where id = " + orderId, String.class);
		assertTrue(expectedState.equals(dbState));
	}

	@Test
	public void placeNewOrderTest() {

		String customerName = "Semen";
		KeyHolder keyHolder = insertCustomer(customerName);

		Long customerId = keyHolder.getKey().longValue();
		Customer customer = new Customer();
		customer.setId(customerId);
		customer.setName(customerName);

		keyHolder = insertAddress("1", "2", "3", "4", "5");
		Long addressId = keyHolder.getKey().longValue();
		Address orderAddress = new Address(addressId, "1", "2", "3", "4", "5");

		List<Long> pizzas = new ArrayList<Long>();
		pizzas.add(insertPizza(10.0, Pizza.Type.MEAT, "PizzaOne").getId());
		pizzas.add(insertPizza(10.0, Pizza.Type.MEAT, "PizzaTwo").getId());

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

	private Pizza insertPizza(double excpectedPrice, Pizza.Type excpectedType, String pizzaName) {
		final String sql = "INSERT INTO pizzas (id, name, pizzaType, price) VALUES (nextval('hibernate_sequence'), '"
				+ pizzaName + "', '" + excpectedType + "', " + excpectedPrice + ")";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sql, new String[] { "id" });
			}
		}, keyHolder);
		return new Pizza(keyHolder.getKey().longValue(), pizzaName, excpectedPrice, excpectedType);
	}

	private KeyHolder insertAddress(String one, String two, String three, String four, String five) {
		KeyHolder keyHolder;
		final String sqlAddress = "INSERT INTO addresses (id, city, street, house, flat, phoneNumber) VALUES (nextval('hibernate_sequence'), '"
				+ one + "','" + two + "','" + three + "','" + four + "','" + five + "')";
		keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlAddress, new String[] { "id" });
			}
		}, keyHolder);
		return keyHolder;
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

	private KeyHolder insertOrder(Long customerId, Long addressId, String orderDate, int pizzaAmmount) {
		KeyHolder keyHolder;
		final String sqlOrder = "INSERT INTO orders (id, date,pizzasammount, state,address_id,customer_id) VALUES (nextval('hibernate_sequence'), '"
				+ orderDate + "', " + pizzaAmmount + ", 'org.study.pizzaservice.domain.order.state.NewState',"
				+ addressId + "," + customerId + ")";
		keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlOrder, new String[] { "id" });
			}
		}, keyHolder);
		return keyHolder;
	}

}
