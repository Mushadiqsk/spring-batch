package com.spring.batch.csv.SpringBatchCSV.config.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.spring.batch.csv.SpringBatchCSV.model.Order;

public class OrderCustomReader implements ItemReader<Order>{

	 String csvPath = "src/main/resources/order.csv";
	 List<Order> orders;
	 private int nextOrderIndex;

	public OrderCustomReader() {
		Pattern pattern = Pattern.compile(",");

		try (Stream<String> lines = Files.lines(Paths.get(csvPath))) {
			orders = lines.skip(1).map(line -> {
			    String[] arr = pattern.split(line);
			    return new Order(
			        arr[0], 
			        arr[1], 
			        Long.parseLong(arr[2]),Long.parseLong(arr[3]),Long.parseLong(arr[4]));
			  }).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Order read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub

		Order nextOrder = null;
		 
        if (nextOrderIndex < orders.size()) {
            nextOrder = orders.get(nextOrderIndex);
            nextOrderIndex++;
        }
        else {
        	nextOrderIndex = 0;
        }
 
        return nextOrder;
        }

}
