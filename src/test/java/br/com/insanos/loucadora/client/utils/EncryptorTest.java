package br.com.insanos.loucadora.client.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EncryptorTest {
    private static final String PASSWORD_HASHED = "f9320baf0249169e73850cd6156ded0106e2bb6ad8cab01b7bbbebe6d1065317";
    @InjectMocks
    private Encryptor encryptor;

    @Test
    void whenEncrypting_thenPasswordHashedMustMatch() {
        ReflectionTestUtils.setField(encryptor, "key", "foo");
        var response = encryptor.encrypt("bar");
        assertThat(PASSWORD_HASHED).isEqualTo(response);
    }
}