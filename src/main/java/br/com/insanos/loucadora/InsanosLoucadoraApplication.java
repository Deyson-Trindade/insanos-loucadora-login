package br.com.insanos.loucadora;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableWebFlux
@OpenAPIDefinition(info = @Info(title = "Client Manager", version = "1.0", description = "API responsible to manage the clients accounts."))
public class InsanosLoucadoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsanosLoucadoraApplication.class, args);
	}

}
