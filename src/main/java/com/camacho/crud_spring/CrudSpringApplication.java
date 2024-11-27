package com.camacho.crud_spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.camacho.crud_spring.model.Course;
import com.camacho.crud_spring.repository.CourseRepository;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(CourseRepository repository){
		return args -> {
			repository.deleteAll();
			Course c1 = new Course();
			c1.setName("Angular");
			c1.setCategory("Frontend");
			repository.save(c1);
			Course c2 = new Course();
			c2.setName("Spring");
			c2.setCategory("Backend");
			repository.save(c2);
		};
	}
}
