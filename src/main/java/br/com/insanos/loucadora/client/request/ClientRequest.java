package br.com.insanos.loucadora.client.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest implements Client {
    private String name;
    private String documentNumber;
    private String password;
}
