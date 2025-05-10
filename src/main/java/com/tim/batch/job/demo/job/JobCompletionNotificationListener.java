package com.tim.batch.job.demo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tim.batch.job.demo.model.Person;

/**
 * Custom component that is called once the step function completes.
 */
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate template;

	/**
	 * Custom constructor using the jdbcTemplate.
	 * 
	 * @param jdbcTemplate
	 */
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.template = jdbcTemplate;
	}

	/**
	 * Checks if the job was completed successfully, and if it is then query the
	 * database and print the values from each record.
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results !!!");
		}

		template.query("SELECT first_name, last_name FROM people", new DataClassRowMapper<>(Person.class))
				.forEach(person -> log.info("Found <{}> in the database.", person));
	}

}
