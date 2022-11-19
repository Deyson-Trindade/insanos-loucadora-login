package br.com.insanos.loucadora.client.utils;

import br.com.caelum.stella.validation.CPFValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {
    @Bean
    public CPFValidator cpfValidator() {
        return new CPFValidator();
    }
}
