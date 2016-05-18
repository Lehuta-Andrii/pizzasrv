package org.study.pizzaservice.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.study.pizzaservice.web.infrastructure.Controller;

public class HelloController implements Controller {

    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
	try(PrintWriter out = resp.getWriter()){
	out.println("hello fdjfgl;a");
	}catch(Exception e){
	    
	}
    }

}
