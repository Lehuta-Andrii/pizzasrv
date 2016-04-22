package org.study.pizzaservice.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public class RegistratedCustomer extends Customer {

    @Temporal(value = TemporalType.DATE)
    private Date date;

    public RegistratedCustomer() {
	this.date = new Date();
    }

    /**
     * @return the date
     */
    public Date getDate() {
	return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
	this.date = date;
    }
    
    
    
    
}
