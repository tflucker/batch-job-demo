package com.tim.batch.job.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class which runs the application.
 */
@SpringBootApplication
public class BatchJobDemoApplication {

	/**
	 * Runs the application and exits the VM once the batch job has completed to
	 * prevent a memory leak or infinitely running the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(BatchJobDemoApplication.class, args)));
	}

}
