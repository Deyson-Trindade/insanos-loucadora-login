package br.com.insanos.loucadora.client.repository;

import br.com.insanos.loucadora.client.document.ClientDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClientRepository extends ReactiveMongoRepository<ClientDocument, String> {
}
