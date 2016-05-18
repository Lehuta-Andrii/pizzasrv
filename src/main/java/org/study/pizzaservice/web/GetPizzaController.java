package org.study.pizzaservice.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.study.pizzaservice.repository.PizzaRepository;
import org.study.pizzaservice.web.infrastructure.Controller;

@org.springframework.stereotype.Controller
public class GetPizzaController implements Controller {

    @Autowired
    private PizzaRepository pizzaRepository;
    
    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
	try(PrintWriter out = resp.getWriter()){
	out.println(pizzaRepository.getPizzaByID(14));
	}catch(Exception e){
	    
	}

    }

}
