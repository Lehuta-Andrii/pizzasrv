package org.study.pizzaservice.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.service.PizzaShopTemplate;

@Controller
public class OrderController {
	
	@Autowired
	PizzaShopTemplate pizzaShop;
	
//	@ModelAttribute("orderForm")
//	public void buildOrderForm(Model model){
//				
//		OrderForm orderForm = new OrderForm();
//		
//		int i=0;
//		for(Pizza pizza: pizzaShop.showMenu()){
//			orderForm.getPizzas().put(pizza.getId(), ++i + "");
//		}
//		
//		model.addAttribute("orderForm", orderForm);
//	
//	}
	
	
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String orderForm(Model model){
		model.addAttribute("pizzasList", pizzaShop.showMenu());
		
		return "pizza-order-form";
	}

	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String makeOrder(@ModelAttribute("orderForm") OrderForm result, BindingResult res){
		System.out.println(result.getPizzas());
		return "redirect:/order";
	}
	
	

}
