package com.crja.tasks_mngr_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.crja.tasks_mngr_api"})
public class TasksMngrApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasksMngrApiApplication.class, args);
	}

}
