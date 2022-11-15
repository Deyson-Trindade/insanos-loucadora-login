package br.com.insanos.loucadora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableWebFlux
public class InsanosLoucadoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsanosLoucadoraApplication.class, args);
	}

}
