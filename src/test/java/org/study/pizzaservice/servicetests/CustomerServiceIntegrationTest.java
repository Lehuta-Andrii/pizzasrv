package org.study.pizzaservice.servicetests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.service.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/H2Repository.xml" })
public class CustomerServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private CustomerService customerService;

	private DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	
	@Autowired
	private JpaTransactionManager  transactionManager;
	
	@Test
	public void getCostumerByIdTest() {

		String customerName = "Semen";

		KeyHolder keyHolder = insertCustomer(customerName);

		Long customerId = keyHolder.getKey().longValue();

		keyHolder =  insertAddress("1", "2", "3", "4", "5");

		Long addressId = keyHolder.getKey().longValue();

		final String sqlAddressCustomers = "INSERT INTO customers_addresses VALUES (" + customerId + "," + addressId
				+ ")";
		jdbcTemplate.update(sqlAddressCustomers);

		Customer customer = customerService.getCustomerById(customerId);
		assertNotNull(customer);
		assertEquals(customer.getName(), "Semen");
		assertTrue(customer.getAddresses().get(0).equals(new Address(addressId, "1", "2", "3", "4", "5")));
	}

	@Test
	public void testAddCustomer() {

		Customer customer = new Customer("Petro", Arrays.asList(new Address("1", "2", "3", "4", "5")));

		assertTrue(customerService.addCustomer(customer));
		
		transactionManager.getTransaction(def).flush();

		Customer inDbCustomer = jdbcTemplate.queryForObject("select * from customers where id = ?",
				new Object[] { customer.getId() }, new CustomerRowMapper());

		List<Address> inDbAddresses = jdbcTemplate.query(
				"select * from addresses left join customers_addresses on addresses.id = customers_addresses.Addresses_id where customer_id = ?",
				new Object[] { customer.getId() }, new AddressRowMapper());

		assertNotNull(inDbCustomer);

		inDbCustomer.setAdresses(inDbAddresses);

		assertEquals(customer, inDbCustomer);

	}

	@Test(expected = IncorrectResultSizeDataAccessException.class)
	public void testRemoveCustomer() {

		String customerName = "Semen";

		KeyHolder keyHolder = insertCustomer(customerName);

		Long id = keyHolder.getKey().longValue();

		Customer customer = new Customer(id, customerName);

		assertTrue(customerService.removeCustomer(customer));

		transactionManager.getTransaction(def).flush();
		
		jdbcTemplate.queryForObject("select * from customers where id = ?", new Object[] { customer.getId() },
				new CustomerRowMapper());

	}

	@Test
	public void addAddressTest() {
		String customerName = "Semen";

		KeyHolder keyHolder = insertCustomer(customerName);

		Long id = keyHolder.getKey().longValue();

		Customer customer = new Customer(id, customerName);
		Address address = new Address("1", "2", "3", "4", "5");

		assertTrue(customerService.addAddress(customer, address));
		
		transactionManager.getTransaction(def).flush();

		List<Address> inDbAddresses = jdbcTemplate.query(
				"select * from addresses left join customers_addresses on addresses.id = customers_addresses.Addresses_id where customer_id = ?",
				new Object[] { customer.getId() }, new AddressRowMapper());
		address.setId(inDbAddresses.get(0).getId());

		assertTrue(inDbAddresses.contains(address));

	}

	@Test
	public void removeAddressTest() {
		
		String customerName = "Semen";

		KeyHolder keyHolder = insertCustomer(customerName);

		Long customerId = keyHolder.getKey().longValue();

		keyHolder = insertAddress("1", "2", "3", "4", "5");

		Long addressId = keyHolder.getKey().longValue();

		final String sqlAddressCustomers = "INSERT INTO customers_addresses VALUES (" + customerId + "," + addressId
				+ ")";
		jdbcTemplate.update(sqlAddressCustomers);

		Address addr = new Address(addressId, "1", "2", "3", "4", "5");
		Customer customer = new Customer(customerId, "Semen", new ArrayList<Address>() {
			private static final long serialVersionUID = 1L;

			{
				add(addr);
			}
		});

		assertTrue(customerService.removeAddress(customer, addr));
		
		transactionManager.getTransaction(def).flush();

		int numberOfAddressesAfterRemove = jdbcTemplate.queryForObject(
				"select count(*) from customers_addresses where customer_id = " + customerId, Integer.class);

		assertEquals(numberOfAddressesAfterRemove, 0);
	}

	@Test
	public void testGetCustomers() {

		String fristCustomerName = "Semen";

		KeyHolder keyHolder = insertCustomer(fristCustomerName);

		Long customerId = keyHolder.getKey().longValue();
		
		Customer firstCustomer = new Customer(customerId, fristCustomerName);
		
		
		
		String secondCustomerName = "Petro";

		keyHolder = insertCustomer(secondCustomerName);

		customerId = keyHolder.getKey().longValue();
		
		Customer secondCustomer = new Customer(customerId, secondCustomerName);
		
		
		List<Customer> dbCustomers = customerService.getCustomers();
		assertTrue(dbCustomers.contains(firstCustomer));
		assertTrue(dbCustomers.contains(secondCustomer));

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
	
	private KeyHolder insertAddress(String one, String two, String three, String four, String five) {
		KeyHolder keyHolder;
		final String sqlAddress = "INSERT INTO addresses (id, city, street, house, flat, phoneNumber) VALUES (nextval('hibernate_sequence'), '"+one+"','"+two+"','"+three+"','"+four+"','"+five+"')";
		keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(sqlAddress, new String[] { "id" });
			}
		}, keyHolder);
		return keyHolder;
	}
	
}
