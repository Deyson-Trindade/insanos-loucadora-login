package br.com.insanos.loucadora.client.repository;

import br.com.insanos.loucadora.client.document.ClientDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveMongoRepository<ClientDocument, String> {
    Mono<ClientDocument> findByDocumentNumberAndActiveAccountIsTrue(String idClient);
}
