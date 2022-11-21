package br.com.insanos.loucadora.client.handler;

import br.com.insanos.loucadora.client.document.ClientDocument;
import br.com.insanos.loucadora.client.request.ClientRequest;
import br.com.insanos.loucadora.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
public class ClientHandler {

    private final ClientService service;

    public Mono<ServerResponse> createClient(final ServerRequest request) {
        return request.bodyToMono(ClientRequest.class)
                .flatMap(service::createClient)
                .flatMap(this::getBodyBuilder);
    }

    private Mono<ServerResponse> getBodyBuilder(ClientDocument document) {
        try {
            var uri = new URI(String.format("/document/%s", document.getId()));
            return ServerResponse.created(uri).bodyValue(document);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Mono<ServerResponse> getClient(ServerRequest request) {
        var idClient = request.pathVariable("idClient");
        return service.getClient(idClient)
                .flatMap(client -> ServerResponse.ok().bodyValue(client))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteClient(ServerRequest request) {
        var idClient = request.pathVariable("idClient");
        return service.inactivateAccount(idClient)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> authenticate(ServerRequest request) {
        return request.bodyToMono(ClientRequest.class)
                .flatMap(service::verifyClientAuthenticy)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.ok().bodyValue(false));
    }
}

