package br.com.insanos.loucadora.client.router;

import br.com.insanos.loucadora.client.document.ClientDocument;
import br.com.insanos.loucadora.client.handler.ClientHandler;
import br.com.insanos.loucadora.client.request.ClientRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
@RequiredArgsConstructor
public class ClientRouter {
    private final ClientHandler handler;


    @Bean
    @RouterOperations(
            {
                    @RouterOperation(path = "/client"
                            , produces = {
                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST, beanClass = ClientHandler.class, beanMethod = "createClient",
                            operation = @Operation(operationId = "creatClient", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation")},
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = ClientRequest.class)))
                            )
                    )
            }
    )
    public RouterFunction<ServerResponse> handler() {
        return route()
                .POST("/client", handler::createClient)
                .GET("/client/{idClient}", handler::getClient)
                .DELETE("/client/{idClient}", handler::deleteClient)
                .build();
    }

}
