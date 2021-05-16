package com.spring.batch.csv.SpringBatchCSV.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.spring.batch.csv.SpringBatchCSV.config.writers.OrderExcelWriter;
import com.spring.batch.csv.SpringBatchCSV.config.writers.OrderTextWriter;
import com.spring.batch.csv.SpringBatchCSV.model.Order;
import com.spring.batch.csv.SpringBatchCSV.processor.OrderLogProceseeor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Value("${spring.datasource.chunk}")
	int chunk;

	
	
	@Bean
	public Job readCsvJob() throws Exception{
		
		return jobBuilderFactory.get("readOrderCSV")
				.incrementer(new RunIdIncrementer())
				.start(step())
				.build();
	}

	private Step step() throws Exception{
			return stepBuilderFactory.get("step")
					.<Order,Order> chunk(chunk)
					.reader(orderCSVReader())
					.processor(new OrderLogProceseeor())
			        .writer(compositeItemWriter())
			        .build();
		
	}

	private ItemReader<Order> orderCSVReader() {
		// TODO Auto-generated method stub
		
		  FlatFileItemReader<Order> itemReader = new FlatFileItemReader<Order>();
		  itemReader.setLineMapper(lineMapper()); itemReader.setLinesToSkip(1);
		  itemReader.setResource(new ClassPathResource("order.csv"));
		 
	        return itemReader;
	}
	
	   @Bean
	    public LineMapper<Order> lineMapper() {
	        DefaultLineMapper<Order> lineMapper = new DefaultLineMapper<Order>();
	        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
	        lineTokenizer.setDelimiter(",");
	        lineTokenizer.setNames(new String[] { "anzsic06", "area", "year", "geo_count", "ec_count"});
	        lineTokenizer.setIncludedFields(new int[] { 0, 1, 2, 3, 4  });
	        BeanWrapperFieldSetMapper<Order> fieldSetMapper = new BeanWrapperFieldSetMapper<Order>();
	        fieldSetMapper.setTargetType(Order.class);
	        lineMapper.setLineTokenizer(lineTokenizer);
	        lineMapper.setFieldSetMapper(fieldSetMapper);
	        return lineMapper;
	    }
	
	
	@Bean(destroyMethod="")
	public CompositeItemWriter<Order> compositeItemWriter() throws Exception {
		CompositeItemWriter<Order> compositeItemWriter = new CompositeItemWriter<>();

		List<ItemWriter<? super Order>> itemWriters = new ArrayList<>();

		/*
		 * itemWriters.add(new OrderTextWriter()); itemWriters.add(new
		 * OrderJsonWriter()); itemWriters.add(new OrderXMLWriter());
		 * itemWriters.add(new OrderExcelWriter());
		 */

		itemWriters.add(txtFileItemWriter());
		itemWriters.add(jsonFileItemWriter());
		itemWriters.add(xmlFileItemWriter());
		itemWriters.add(new OrderExcelWriter());
		
		compositeItemWriter.setDelegates(itemWriters);
		compositeItemWriter.afterPropertiesSet();
		return compositeItemWriter;

	}

	
	@Bean
	public OrderTextWriter<Order> txtFileItemWriter() throws Exception {
		OrderTextWriter<Order> orderTextWriter = new OrderTextWriter<>();

		orderTextWriter.setLineAggregator(new PassThroughLineAggregator<Order>());
		String outFilePath = File.createTempFile("Order", ".txt").getAbsolutePath();

		orderTextWriter.setResource(new FileSystemResource(outFilePath));

		orderTextWriter.afterPropertiesSet();

		return orderTextWriter;
	}

	@Bean
	public OrderTextWriter<Order> jsonFileItemWriter() throws Exception {
		OrderTextWriter<Order> orderJsonWriter = new OrderTextWriter<>();

		orderJsonWriter.setLineAggregator(new OrderJsonLineAggregator());
		String outFilePath = File.createTempFile("Order", ".json").getAbsolutePath();

		orderJsonWriter.setResource(new FileSystemResource(outFilePath));

		orderJsonWriter.afterPropertiesSet();

		return orderJsonWriter;
	}

	@Bean(destroyMethod="")
	public StaxEventItemWriter<Order> xmlFileItemWriter() throws Exception {
		StaxEventItemWriter<Order> staxEventItemWriter = new StaxEventItemWriter<>();

		Map<String, Class> aliases = new HashMap<>();
		aliases.put("Order", Order.class);

		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(aliases);

		staxEventItemWriter.setMarshaller(marshaller);
		String outFilePath = File.createTempFile("Order", ".xml").getAbsolutePath();
		staxEventItemWriter.setResource(new FileSystemResource(outFilePath));
		
		staxEventItemWriter.afterPropertiesSet();

		return staxEventItemWriter;
	}


	 
	
}
