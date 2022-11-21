package br.com.insanos.loucadora.client.router;

import br.com.insanos.loucadora.client.document.ClientDocument;
import br.com.insanos.loucadora.client.handler.ClientHandler;
import br.com.insanos.loucadora.client.request.ClientRequest;
import br.com.insanos.loucadora.client.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ClientRouter.class, ClientHandler.class})
@WebFluxTest
class ClientRouterTest {
    private static final String DOCUMENT_NUMBER = "16582640977";
    private static final String PASSWORD = "@Ostrich123";
    private static final String CLIENT_NAME = "Jhon Foe Doe";
    private static final String CLIENT_ID = "7fa33bb4-a73c-4e16-93bf-4d66c65300f3";
    @Autowired
    private ApplicationContext context;
    private WebTestClient webTestClient;
    @MockBean
    private ClientService service;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void whenCallingRouterGetClient_then_ShouldMatchTheInformation() {
        final var document = ClientDocument.builder()
                .id(CLIENT_ID)
                .name(CLIENT_NAME)
                .documentNumber(DOCUMENT_NUMBER)
                .password(PASSWORD)
                .activeAccount(true)
                .createdAt(LocalDateTime.now())
                .build();
        final var documentMono = Mono.just(document);

        Mockito.doReturn(documentMono)
                .when(service)
                .getClient(Mockito.any());

        webTestClient.get()
                .uri("/client/16582640977/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientDocument.class)
                .value(routerResponse -> {
                    assertThat(CLIENT_ID).isEqualTo(routerResponse.getId());
                    assertThat(DOCUMENT_NUMBER).isEqualTo(routerResponse.getDocumentNumber());
                    assertThat(CLIENT_NAME).isEqualTo(routerResponse.getName());
                });
    }

    @Test
    void whenCallingRouterCreateClient_then_ShouldCreateAndReturnANewClient() {
        final var clientRequest = ClientRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .name(CLIENT_NAME)
                .password(PASSWORD)
                .build();
        final var monoClientRequest = Mono.just(clientRequest);

        //StepVerifier.create(monoClientRequest).
        //para testar mono e flux
        final var clientDocument = ClientDocument.builder()
                .id(CLIENT_ID)
                .name(CLIENT_NAME)
                .documentNumber(DOCUMENT_NUMBER)
                .password(PASSWORD)
                .activeAccount(true)
                .createdAt(LocalDateTime.now())
                .build();
        final var monoClientDocument = Mono.just(clientDocument);

        Mockito.doReturn(monoClientDocument)
                .when(service)
                .createClient(Mockito.any());

        webTestClient.post()
                .uri("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(monoClientRequest, ClientRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClientDocument.class)
                .value(clientResponse -> assertThat(CLIENT_NAME).isEqualTo(clientResponse.getName()));
    }

    @Test
    void whenCallingRouterDeleteClient_then_ShouldInactivateAccount() {
        final var clientDocument = ClientDocument.builder()
                .id(CLIENT_ID)
                .name(CLIENT_NAME)
                .documentNumber(DOCUMENT_NUMBER)
                .password(PASSWORD)
                .activeAccount(false)
                .createdAt(LocalDateTime.now())
                .build();

        final var monoClientDocument = Mono.just(clientDocument);

        Mockito.doReturn(monoClientDocument)
                .when(service)
                .inactivateAccount(Mockito.any());

        webTestClient.delete()
                .uri(String.format("/client/%S", DOCUMENT_NUMBER))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientDocument.class)
                .value(clientResponse -> assertThat(clientResponse.isActiveAccount()).isFalse());
    }

}