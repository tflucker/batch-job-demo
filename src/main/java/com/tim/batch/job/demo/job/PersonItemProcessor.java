package com.tim.batch.job.demo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.tim.batch.job.demo.model.Person;

/**
 * Processes each each DTO object created from the sample-data.csv file.
 */
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

	private static Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

	/**
	 * Each record has its firstName and lastName converted into upper case letters.
	 */
	@Override
	public Person process(final Person item) throws Exception {
		final String firstName = item.firstName().toUpperCase();
		final String lastName = item.lastName().toUpperCase();

		final Person transformedPerson = new Person(firstName, lastName);

		log.info("Converting ({}) into ({})", item, transformedPerson);

		return transformedPerson;
	}

}
