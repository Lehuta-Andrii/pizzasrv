package org.study.pizzaservice.domain;

import java.io.Serializable;

public class State implements Serializable{

    private final String state;
    
    public State(String state) {
	this.state = state;
    }

    /**
     * @return the state
     */
    public String getState() {
	return state;
    }
       
}
