package br.com.insanos.loucadora.client.utils;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class Encryptor {
    @Value("${encrypt.key}")
    private String key;

    public String encrypt(final String valueToDigest) {
            return Hashing.hmacSha256(key.getBytes(StandardCharsets.UTF_8))
                    .hashString(valueToDigest, StandardCharsets.UTF_8)
                    .toString();
    }
}
