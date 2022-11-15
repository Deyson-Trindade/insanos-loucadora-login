package br.com.insanos.loucadora.client.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDocument {

    @Id
    private String id;
    private String name;
    private String documentNumber;
    private String password;
    private LocalDateTime createdAt;
}
