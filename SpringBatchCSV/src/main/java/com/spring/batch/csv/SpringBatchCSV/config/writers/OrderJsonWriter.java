package com.spring.batch.csv.SpringBatchCSV.config.writers;

import java.io.File;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.batch.csv.SpringBatchCSV.model.Order;

public class OrderJsonWriter implements ItemWriter<Order>{

	@Override
	public void write(List<? extends Order> orders) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
    	mapper.writeValue(new File(File.createTempFile("Order", ".json").getAbsolutePath()), orders);
	}

}
