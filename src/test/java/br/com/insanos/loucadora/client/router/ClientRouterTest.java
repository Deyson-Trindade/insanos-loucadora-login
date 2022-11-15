package br.com.insanos.loucadora.client.router;

import br.com.insanos.loucadora.client.handler.ClientHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ClientRouter.class, ClientHandler.class})
@WebFluxTest
class ClientRouterTest {

    @Autowired
    private ApplicationContext context;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void a() {
        final String name = "Deyson";
        final String response = "Hello " + name;
        webTestClient.get()
                .uri("/" + name)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(routerResponse -> assertThat(response).isEqualTo(routerResponse));
    }

}