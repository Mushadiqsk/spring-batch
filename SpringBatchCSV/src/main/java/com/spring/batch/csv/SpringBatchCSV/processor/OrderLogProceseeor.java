package com.spring.batch.csv.SpringBatchCSV.processor;

import org.springframework.batch.item.ItemProcessor;

import com.spring.batch.csv.SpringBatchCSV.model.Order;

public class OrderLogProceseeor implements ItemProcessor<Order,Order> {

	@Override
	public Order process(Order order) throws Exception {
		return order;
	}
	
}