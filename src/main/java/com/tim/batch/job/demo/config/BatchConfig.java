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

@Configuration
public class BatchConfig {

	
	@Bean
	public FlatFileItemReader<Person> reader(){
		return new FlatFileItemReaderBuilder<Person>()
				.name("personReader")
				.resource(new ClassPathResource("sample-data.csv"))
				.delimited()
				.names("firstName", "lastName")
				.targetType(Person.class)
				.build();
	}
	
	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}
	
	@Bean
	public JdbcBatchItemWriter<Person> writer(DataSource datasource){
		return new JdbcBatchItemWriterBuilder<Person>()
				.sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
				.dataSource(datasource)
				.beanMapped()
				.build();
	}
	
	@Bean
	public Job importUserJob(JobRepository jobRepo, Step step1, JobCompletionNotificationListener listener) {
		return new JobBuilder("importUserJob", jobRepo)
				.listener(listener)
				.start(step1)
				.build();
	}
	
	@Bean
	public Step step1(JobRepository jobRepo, DataSourceTransactionManager transactionManager, FlatFileItemReader<Person> reader, PersonItemProcessor processor, JdbcBatchItemWriter<Person> writer) {
		return new StepBuilder("step1", jobRepo)
				.<Person, Person>chunk(3, transactionManager)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}
}
