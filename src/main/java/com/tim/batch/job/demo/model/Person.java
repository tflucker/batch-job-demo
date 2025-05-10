package com.tim.batch.job.demo.model;

/**
 * Serves as the DTO (data transfer object) to convert each line in the
 * sample-data.csv into a this object
 */
public record Person(String firstName, String lastName) {

}
