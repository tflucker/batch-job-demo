# batch-job-demo
Example of how to use use a batch processing with spring and java 17 which was created using the following [tutorial](https://spring.io/guides/gs/batch-processing).


How to run locally: 

1. Verify that the `sample-data.csv` file in the `src/main/resources/` folder is present and has comma separated values. Each line should have two values (first_name, last_name)
2. Run the `mvn clean package` command to generate a jar
3. Run the `java -jar target/batch-job-demo-0.0.1-SNAPSHOT.jar` to run the file 


#### Example Output:

```
2025-05-09T19:44:20.711-04:00  INFO 38292 --- [batch-job-demo] [           main] o.s.b.c.l.s.TaskExecutorJobLauncher      : Job: [SimpleJob: [name=importUserJob]] launched with the following parameters: [{}]
2025-05-09T19:44:20.737-04:00  INFO 38292 --- [batch-job-demo] [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
2025-05-09T19:44:20.770-04:00  INFO 38292 --- [batch-job-demo] [           main] c.t.batch.job.demo.PersonItemProcessor   : Converting (Person[firstName=Jill, lastName=Doe]) intto (Person[firstName=JILL, lastName=DOE])
2025-05-09T19:44:20.772-04:00  INFO 38292 --- [batch-job-demo] [           main] c.t.batch.job.demo.PersonItemProcessor   : Converting (Person[firstName=Joe, lastName=Doe]) intto (Person[firstName=JOE, lastName=DOE])
2025-05-09T19:44:20.772-04:00  INFO 38292 --- [batch-job-demo] [           main] c.t.batch.job.demo.PersonItemProcessor   : Converting (Person[firstName=Justin, lastName=Doe]) intto (Person[firstName=JUSTIN, lastName=DOE])
2025-05-09T19:44:20.783-04:00  INFO 38292 --- [batch-job-demo] [           main] c.t.batch.job.demo.PersonItemProcessor   : Converting (Person[firstName=Jane, lastName=Doe]) intto (Person[firstName=JANE, lastName=DOE])
2025-05-09T19:44:20.783-04:00  INFO 38292 --- [batch-job-demo] [           main] c.t.batch.job.demo.PersonItemProcessor   : Converting (Person[firstName=John, lastName=Doe]) intto (Person[firstName=JOHN, lastName=DOE])
2025-05-09T19:44:20.788-04:00  INFO 38292 --- [batch-job-demo] [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 50ms
2025-05-09T19:44:20.791-04:00  INFO 38292 --- [batch-job-demo] [           main] .j.d.c.JobCompletionNotificationListener : !!! JOB FINISHED! Time to verify the results !!!
2025-05-09T19:44:20.798-04:00  INFO 38292 --- [batch-job-demo] [           main] .j.d.c.JobCompletionNotificationListener : Found <Person[firstName=JILL, lastName=DOE]> in the database.
2025-05-09T19:44:20.798-04:00  INFO 38292 --- [batch-job-demo] [           main] .j.d.c.JobCompletionNotificationListener : Found <Person[firstName=JOE, lastName=DOE]> in the database.
2025-05-09T19:44:20.798-04:00  INFO 38292 --- [batch-job-demo] [           main] .j.d.c.JobCompletionNotificationListener : Found <Person[firstName=JUSTIN, lastName=DOE]> in the database.
2025-05-09T19:44:20.798-04:00  INFO 38292 --- [batch-job-demo] [           main] .j.d.c.JobCompletionNotificationListener : Found <Person[firstName=JANE, lastName=DOE]> in the database.
2025-05-09T19:44:20.798-04:00  INFO 38292 --- [batch-job-demo] [           main] .j.d.c.JobCompletionNotificationListener : Found <Person[firstName=JOHN, lastName=DOE]> in the database.
2025-05-09T19:44:20.802-04:00  INFO 38292 --- [batch-job-demo] [           main] o.s.b.c.l.s.TaskExecutorJobLauncher      : Job: [SimpleJob: [name=importUserJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 62ms
```