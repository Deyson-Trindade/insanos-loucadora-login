package br.com.insanos.loucadora.client.service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.insanos.loucadora.client.document.ClientDocument;
import br.com.insanos.loucadora.client.repository.ClientRepository;
import br.com.insanos.loucadora.client.request.ClientRequest;
import br.com.insanos.loucadora.client.utils.Encryptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    private static final String DOCUMENT_NUMBER = "16582640977";
    private static final String PASSWORD = "@Ostrich123";
    private static final String CLIENT_NAME = "Jhon Foe Doe";
    private static final String CLIENT_ID = "7fa33bb4-a73c-4e16-93bf-4d66c65300f3";
    @InjectMocks
    private ClientService service;
    @Mock
    private ClientRepository repository;
    @Mock
    private CPFValidator cpfValidator;
    @Mock
    private Encryptor encryptor;

    @Test
    void whenCreateClient_thenSaveAndReturnTheClient() {
        final var document = ClientDocument.builder()
                .id(CLIENT_ID)
                .name(CLIENT_NAME)
                .documentNumber(DOCUMENT_NUMBER)
                .password(PASSWORD)
                .activeAccount(true)
                .createdAt(LocalDateTime.now())
                .build();
        final var documentMono = Mono.just(document);

        final var clientRequest = ClientRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .name(CLIENT_NAME)
                .password(PASSWORD)
                .build();

        Mockito.doNothing()
                .when(cpfValidator)
                .assertValid(Mockito.any());

        Mockito.doReturn(documentMono)
                .when(repository)
                .save(Mockito.any());

        var responseMono = service.createClient(clientRequest);

        StepVerifier.create(responseMono)
                .assertNext(response -> {
                    assertEquals(DOCUMENT_NUMBER, response.getDocumentNumber());
                    assertEquals(CLIENT_ID, response.getId());
                })
                .verifyComplete();
    }

    @Test
    void whenFindClientByDocumentNumer_thenAssertDataIscommingBack() {
        var clientDocument = ClientDocument.builder()
                .id(CLIENT_ID)
                .name(CLIENT_NAME)
                .activeAccount(true)
                .createdAt(LocalDateTime.now())
                .documentNumber(DOCUMENT_NUMBER)
                .password(PASSWORD)
                .build();
        var monoClientDocument = Mono.just(clientDocument);

        Mockito.doReturn(monoClientDocument)
                .when(repository)
                .findByDocumentNumberAndActiveAccountIsTrue(Mockito.any());

        var responseMono = service.getClient(DOCUMENT_NUMBER);

        StepVerifier.create(responseMono)
                .assertNext(response -> {
                    assertTrue(response.isActiveAccount());
                    assertEquals(DOCUMENT_NUMBER, response.getDocumentNumber());
                    assertEquals(CLIENT_NAME, response.getName());
                })
                .verifyComplete();
    }

    @Test
    void whenInactivateAccount_thenReturnTheClientInactive() {
        var clientDocument = ClientDocument.builder()
                .id(CLIENT_ID)
                .name(CLIENT_NAME)
                .activeAccount(true)
                .createdAt(LocalDateTime.now())
                .documentNumber(DOCUMENT_NUMBER)
                .password(PASSWORD)
                .build();
        var monoClientDocument = Mono.just(clientDocument);

        Mockito.doReturn(monoClientDocument)
                .when(repository)
                .save(Mockito.any());

        clientDocument.setActiveAccount(false);

        Mockito.doReturn(monoClientDocument)
                .when(repository)
                .findByDocumentNumberAndActiveAccountIsTrue(Mockito.any());

        var responseMono = service.inactivateAccount(DOCUMENT_NUMBER);

        StepVerifier.create(responseMono)
                .assertNext(response -> assertFalse(response.isActiveAccount()))
                .verifyComplete();
    }

    @Test
    void whenVerifyingClientAuthenticity_thenReturnTrue() {
        Mockito.doReturn("this is an encrypted password.")
                .when(encryptor)
                .encrypt(Mockito.any());

        var clientDocument = ClientDocument.builder()
                .id(CLIENT_ID)
                .name(CLIENT_NAME)
                .activeAccount(true)
                .createdAt(LocalDateTime.now())
                .documentNumber(DOCUMENT_NUMBER)
                .password(PASSWORD)
                .build();
        var monoClientDocument = Mono.just(clientDocument);

        Mockito.doReturn(monoClientDocument)
                .when(repository)
                .findByDocumentNumberAndPasswordAndActiveAccountIsTrue(Mockito.any(), Mockito.any());

        final var client = ClientRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .name(CLIENT_NAME)
                .password(PASSWORD)
                .build();

        var responseMono = service.verifyClientAuthenticy(client);

        StepVerifier.create(responseMono)
                .expectNext(true)
                .verifyComplete();
    }
}