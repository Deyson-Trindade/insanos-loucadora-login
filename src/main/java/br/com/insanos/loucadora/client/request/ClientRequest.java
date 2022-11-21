package br.com.insanos.loucadora.client.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest implements Client {
    private String name;
    private String documentNumber;
    private String password;
}
