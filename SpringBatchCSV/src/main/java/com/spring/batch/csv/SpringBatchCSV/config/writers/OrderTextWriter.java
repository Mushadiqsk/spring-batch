package com.spring.batch.csv.SpringBatchCSV.config.writers;

import java.util.List;

import org.springframework.batch.item.file.FlatFileItemWriter;

public class OrderTextWriter<Order> extends FlatFileItemWriter<Order>{


	
	@Override
	public String doWrite(List<? extends Order> items) {
		StringBuilder lines = new StringBuilder();
		items.stream().forEach((item) -> {
			lines.append(this.lineAggregator.aggregate(item)).append(this.lineSeparator);
		});
		return lines.toString();
	}

	

	

}
