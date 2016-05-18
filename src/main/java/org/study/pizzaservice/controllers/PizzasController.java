package org.study.pizzaservice.controllers;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.service.PizzaShopTemplate;
import org.study.pizzaservice.service.PizzasService;

@Controller
@RequestMapping("/pizzas")
public class PizzasController {
	
	@Autowired
	private PizzasService pizzaService;
	
	@RequestMapping("/list")
	public String showPizzas(Model model){
		model.addAttribute("pizzasList", pizzaService.getPizzas());
		return "pizzas";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPizza(@ModelAttribute Pizza pizza){
		pizzaService.addPizza(pizza);
		return "redirect:/pizzas/list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String showAddPizzaForm(){
		return "add-pizza-form";
	}
	
	
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String removePizza(@PathVariable Long id){
		pizzaService.deletePizza(pizzaService.getPizzaById(id));
		return "redirect:/pizzas/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String showEditPizzaForm(Model model) {
		model.addAttribute("pizzasList", pizzaService.getPizzas());
		return "edit-pizzas-list";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String editPizzaForm(@ModelAttribute Pizza pizza, @PathVariable Long id) {
		
		pizza.setId(id);
		pizzaService.updatePizza(pizza);
		return "redirect:/pizzas/edit";
	}
	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editTeamPage(@PathVariable Long id) {
		
		ModelAndView modelAndView = new ModelAndView("edit-pizza-form");
		Pizza pizza = pizzaService.getPizzaById(id);
		modelAndView.addObject("pizza",pizza);
		modelAndView.addObject("types", Arrays.asList(Pizza.Type.values()));
		
		return modelAndView;
	}
	
	
}
