package com.spring.batch.csv.SpringBatchCSV.config;

import org.springframework.batch.item.file.transform.LineAggregator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.spring.batch.csv.SpringBatchCSV.model.Order;

public class OrderJsonLineAggregator implements LineAggregator<Order> {

	private ObjectMapper objectMapper = new ObjectMapper();

	{
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	@Override
	public String aggregate(Order order) {

		try {
			return objectMapper.writeValueAsString(order);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error Occured while serializing Order instance : " + order);
		}
	}
}
