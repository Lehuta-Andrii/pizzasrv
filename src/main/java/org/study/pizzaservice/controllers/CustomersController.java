package org.study.pizzaservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.study.pizzaservice.service.CustomerService;


@Controller
@RequestMapping("/customers")
public class CustomersController {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/list")
	public String showCustomers(Model model){
		model.addAttribute("customersList", customerService.getCustomers());
		return "customers";
	}
	
}
