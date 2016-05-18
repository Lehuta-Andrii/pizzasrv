package org.study.pizzaservice.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.service.PizzaShopTemplate;

@Controller
public class OrderController {
	
	@Autowired
	PizzaShopTemplate pizzaShop;
	
	
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public ModelAndView orderForm(){
		ModelAndView model = new ModelAndView("pizza-order-form");
	
		
		PizzaAndAmmount paa = new PizzaAndAmmount();
		
		int i=0;
		for(Pizza pizza: pizzaShop.showMenu()){
			paa.getPizzas().put(pizza, ++i +"");
		}
		
		model.addObject("pizzaAndAmmount", paa);
		
		return model;
	}

	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String makeOrder(@ModelAttribute("pizzaAndAmmount") PizzaAndAmmount result, BindingResult res){
		return "redirect:/order";
	}
	
	

}
