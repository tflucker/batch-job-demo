package com.tim.batch.job.demo.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.tim.batch.job.demo.job.JobCompletionNotificationListener;
import com.tim.batch.job.demo.job.PersonItemProcessor;
import com.tim.batch.job.demo.model.Person;

/**
 * configuration class used to define beans used for the batch job process.
 */
@Configuration
public class BatchConfig {

	/**
	 * Defines the file reader which looks for the "sample-data.csv" file in the
	 * "src/main/resources" folder. Defines two fields which are delimited by a
	 * comma and creates a Person record from the information.
	 * 
	 * @return
	 */
	@Bean
	public FlatFileItemReader<Person> reader() {
		return new FlatFileItemReaderBuilder<Person>().name("personReader")
				.resource(new ClassPathResource("sample-data.csv"))
				.delimited()
				.names("firstName", "lastName")
				.targetType(Person.class)
				.build();
	}

	/**
	 * Defines the PersonItemProcessor which transforms each Person object created
	 * by the batch job.
	 * 
	 * @return
	 */
	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}

	/**
	 * Defines JdbcBatchItemWriter to insert the Person records into a corresponding
	 * database table
	 * 
	 * @param dataSource
	 * @return
	 */
	@Bean
	public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Person>()
				.sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
				.dataSource(dataSource)
				.beanMapped()
				.build();
	}

	/**
	 * Defines the batch job with a name, a custom listener class and a step
	 * function.
	 * 
	 * @param jobRepo
	 * @param step1
	 * @param listener
	 * @return
	 */
	@Bean
	public Job importUserJob(JobRepository jobRepo, Step step1, JobCompletionNotificationListener listener) {
		return new JobBuilder("importUserJob", jobRepo)
				.listener(listener)
				.start(step1)
				.build();
	}

	/**
	 * Defines the step function for the "importUserJob". Creates a chunk size with
	 * an input of Person and an output of Person using the file reader, then the
	 * custom processor and then the database writer.
	 * 
	 * @param jobRepo
	 * @param transactionManager
	 * @param reader
	 * @param processor
	 * @param writer
	 * @return
	 */
	@Bean
	public Step step1(JobRepository jobRepo, DataSourceTransactionManager transactionManager,
			FlatFileItemReader<Person> reader, PersonItemProcessor processor, JdbcBatchItemWriter<Person> writer) {
		return new StepBuilder("step1", jobRepo)
				.<Person, Person>chunk(3, transactionManager)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}
}
