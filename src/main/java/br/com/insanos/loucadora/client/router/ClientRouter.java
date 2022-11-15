package br.com.insanos.loucadora.client.router;

import br.com.insanos.loucadora.client.handler.ClientHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;


@Configuration
public class ClientRouter {

    private final ClientHandler handler;

    public ClientRouter(ClientHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> handler() {
        return route().path("/", builder ->
                        builder.nest(accept(APPLICATION_JSON), routerBuilder -> routerBuilder
                                .GET("{name}", handler::greetings)
                                .POST("/client", handler::createClient)))
                .build();
    }

}
