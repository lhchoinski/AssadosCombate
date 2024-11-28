package com.assadosCombate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
	"com.assadosCombate.repositories"
})
@EntityScan(basePackages = {
		"com.assadosCombate.entities"
})
public class AssadosCombateApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssadosCombateApplication.class, args);
	}

}
