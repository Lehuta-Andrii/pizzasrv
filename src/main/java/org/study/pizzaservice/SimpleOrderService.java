package org.study.pizzaservice;

import java.util.ArrayList;
import java.util.List;

import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Order;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.infrastructure.BeanchMark;
import org.study.pizzaservice.repository.OrderRepository;
import org.study.pizzaservice.repository.PizzaRepository;

public class SimpleOrderService implements OrderService//, ApplicationContextAware
{

//    private ServiceLocator locator = ServiceLocator.getInstance();
    private PizzaRepository pizzaRepository;
    //= (PizzaRepository) locator.lookup("pizzaRepository");
    private OrderRepository orderRepository;
    //= (OrderRepository) locator.lookup("orderRepository");
    // new InMemOrderRepository();

    
//    public SimpleOrderService() {

//    }
    
//    private Order order;
    private Customer customer;

    
    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
	System.out.println(customer);
        this.customer = customer;
    }

    public SimpleOrderService(PizzaRepository pizzaRepository, OrderRepository orderRepository) {
	this.pizzaRepository = pizzaRepository;
	this.orderRepository = orderRepository;
    }

    @BeanchMark
    public Order placeNewOrder(Customer customer, Integer... pizzasID) {
	List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
	Order newOrder = createOrder();
	newOrder.setCustomer(customer);
	newOrder.setPizzas(pizzas);

	orderRepository.saveOrder(newOrder); // set Order Id and save Order to
					     // in-memory list
	return newOrder;
    }

    @BeanchMark
    public Order createOrder(){
	return new Order();
    }
	//Order newOrder = new Order(customer, pizzas);
	//return newOrder;
//	Order order = (Order) appContext.getBean("order");

    private List<Pizza> pizzasByArrOfId(Integer... pizzasID) {
	List<Pizza> pizzas = new ArrayList<Pizza>();

	for (Integer id : pizzasID) {
	    pizzas.add(pizzaRepository.getPizzaByID(id)); 						  // in-memory
	}
	return pizzas;
    }


}
