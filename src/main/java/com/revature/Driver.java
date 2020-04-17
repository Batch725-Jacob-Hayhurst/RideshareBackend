package com.revature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Driver class is the entry point into the Spring Boot Rideshare back end.
 * 
 * @author Judson Higley
 *
 */

@SpringBootApplication
public class Driver {
	/**
	 * The main method of the Driver class.
	 * 
	 * @param args represents any CLI arguments.
	 * @throws Exception
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(Driver.class, args); 
		
	}

}
