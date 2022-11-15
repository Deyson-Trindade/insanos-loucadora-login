package br.com.insanos.loucadora.client.service;

import br.com.insanos.loucadora.client.document.ClientDocument;
import br.com.insanos.loucadora.client.repository.ClientRepository;
import br.com.insanos.loucadora.client.request.ClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    public Mono<ClientDocument> createClient(final ClientRequest client) {

        var document = ClientDocument.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .documentNumber(client.getDocumentNumber())
                .name(client.getName())
                .password(client.getPassword())
                .createdAt(LocalDateTime.now())
                .build();

        return repository.save(document);
    }
}