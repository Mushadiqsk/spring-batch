package com.spring.batch.csv.SpringBatchCSV;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class SpringBatchCsvApplication {


	 @Autowired
	    JobLauncher jobLauncher;
	      
	    @Autowired
	    Job job;
	public static void main(String[] args) {
		System.out.println("testing ....");
		SpringApplication.run(SpringBatchCsvApplication.class, args);
	}

	      
	 
	   // @Scheduled(cron = "0 */1 * * * ?")
	    public void perform() throws Exception
	    {
	    	
	    	System.out.println("inside perform ..");
	        JobParameters params = new JobParametersBuilder()
	                .addString("JobID", String.valueOf(System.currentTimeMillis()))
	                .toJobParameters();
	        jobLauncher.run(job, params);
	    }
}
