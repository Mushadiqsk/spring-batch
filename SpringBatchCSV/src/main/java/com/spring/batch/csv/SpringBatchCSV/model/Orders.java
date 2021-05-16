package com.spring.batch.csv.SpringBatchCSV.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "orders")
public class Orders {

	    private List<Order> orders = null;
	 
	    public List<Order> getOrders() {
	        return orders;
	    }
	 
	    public void setOrders(List<Order> orders) {
	        this.orders = orders;
	    }
}
