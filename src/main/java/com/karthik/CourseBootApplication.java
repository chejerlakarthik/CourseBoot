package com.karthik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 */

/**
 * @author karthikchejerla
 *
 */
@SpringBootApplication
@EnableJpaRepositories
public class CourseBootApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(CourseBootApplication.class, args);

	}

}
