package org.study.pizzaservice.web.infrastructure;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Servlet implementation class DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    private HandlerMapping handlerMapping;
    private ConfigurableApplicationContext repositoryContext;
    private ConfigurableApplicationContext applicationContext;
    private ConfigurableApplicationContext webContext;
	
    
    @Override
    public void init(){
	String configsLocation =  this.getServletContext().getInitParameter("contextConfigLocation");
	String[] contextConfigs = configsLocation.split(" ");
	String webContextLocation = getInitParameter("contextConfigLocation");
	
//	repositoryContext =  new ClassPathXmlApplicationContext("repositoryContext.xml");	
//	applicationContext =  new ClassPathXmlApplicationContext(new String[]{"AppContext.xml"}, repositoryContext);	
	repositoryContext =  new ClassPathXmlApplicationContext(contextConfigs[0]);	
	applicationContext =  new ClassPathXmlApplicationContext(new String[]{contextConfigs[1]}, repositoryContext);	

	webContext = new ClassPathXmlApplicationContext(new String[]{webContextLocation}, applicationContext);
	handlerMapping = new SpringUrlHandlerMapping(webContext);
    }
    
    /**
     * Default constructor.
     */
    public DispatcherServlet() {
	// TODO Auto-generated constructor stub
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
	
	String url = request.getRequestURI();
	String controllerName = getControllerName(url);
	Controller controller = handlerMapping.getController(controllerName);
	
	if (controller != null) {
	    controller.handleRequest(request, response);
	}
    }

    @Override
    public void destroy(){
	webContext.close();
	applicationContext.close();
	repositoryContext.close();
    }
    
    private String getControllerName(String url) {
	return url.substring(url.lastIndexOf("/"));
    }

}
