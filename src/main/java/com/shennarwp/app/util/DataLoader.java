package com.shennarwp.app.util;

import com.shennarwp.app.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class will be called upon running the application by AppApplication
 */
@Configuration
public class DataLoader
{

	/* will at runtime injected by Spring, based on @Profile annotation at Repository classes */
	private final PersonRepository repository;
	DataLoader(PersonRepository repository)
	{
		this.repository = repository;
	}

	/**
	 *  automatically executed at application start
	 *  will read from csv file and store it in repository / database
	 */
	@Bean
	CommandLineRunner initData() {
		return args -> {
			ReaderUtil util = new CSVUtil();
			util.extractPerson(util.readData("static/sample-input.csv"))
					.forEach(repository::save);
		};
	}
}
