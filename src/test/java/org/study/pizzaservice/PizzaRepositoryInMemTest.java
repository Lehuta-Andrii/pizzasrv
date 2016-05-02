package org.study.pizzaservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.repository.PizzaRepository;

/**
 *
 * @author andrii
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {     
    "classpath:/inmemdbContext.xml"}
)
public class PizzaRepositoryInMemTest extends AbstractTransactionalJUnit4SpringContextTests{
    
    @Autowired
    private PizzaRepository pizzaRepository;
    
    @Test
    public void testGetPizzaByID() {
        final String sql
                = "INSERT INTO pizza (name, pizza_type) VALUES ('Vegan', "
                + "'MEAT')";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                new PreparedStatementCreator() {

                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        return con.prepareStatement(sql, new String[] { "id" }); 
                        	//Statement.RETURN_GENERATED_KEYS);
                    }
                }, keyHolder);

        Integer id = keyHolder.getKey().intValue();
        
        Pizza pizza = pizzaRepository.getPizzaByID(id);
        System.out.println(pizza);
    }
    
    @Test
    public void testCreate() {
        Pizza pizza = new Pizza();
        pizza.setName("Marg");
        pizza.setPrice(123.4);
        pizza.setPizzaType(Pizza.type.SEA);
        
        pizza = pizzaRepository.create(pizza);
        Assert.assertNotNull(pizza.getId());
        System.out.println(pizza);
    }
    
}
