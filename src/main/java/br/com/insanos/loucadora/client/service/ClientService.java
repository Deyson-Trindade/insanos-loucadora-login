package br.com.insanos.loucadora.client.service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.insanos.loucadora.client.document.ClientDocument;
import br.com.insanos.loucadora.client.repository.ClientRepository;
import br.com.insanos.loucadora.client.request.Client;
import br.com.insanos.loucadora.client.utils.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;
    private final CPFValidator cpfValidator;
    private final Encryptor encryptor;


    public Mono<ClientDocument> createClient(final Client client) {
        cpfValidator.assertValid(client.getDocumentNumber());
        var document = ClientDocument.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .documentNumber(client.getDocumentNumber())
                .name(client.getName())
                .password(encryptor.encrypt(client.getPassword()))
                .activeAccount(true)
                .createdAt(LocalDateTime.now())
                .build();

        return repository.save(document);
    }

    public Mono<ClientDocument> getClient(final String idClient) {
        return repository.findByDocumentNumberAndActiveAccountIsTrue(idClient);
    }

    public Mono<Boolean> verifyClientAuthenticy(final Client client) {
        return Mono.just(client.getPassword())
                .map(encryptor::encrypt)
                .flatMap(encrypted -> repository.findByDocumentNumberAndPasswordAndActiveAccountIsTrue(client.getDocumentNumber(), encrypted))
                .map(Objects::nonNull);
    }

    public Mono<ClientDocument> inactivateAccount(final String idClient) {
        return repository.findByDocumentNumberAndActiveAccountIsTrue(idClient)
                .flatMap(account -> {
                    account.setActiveAccount(false);
                    account.setUpdatedAt(LocalDateTime.now());
                    return repository.save(account);
                });
    }
}
