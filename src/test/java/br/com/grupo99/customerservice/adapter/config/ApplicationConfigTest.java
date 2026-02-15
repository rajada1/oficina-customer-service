package br.com.grupo99.customerservice.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ApplicationConfig Tests")
class ApplicationConfigTest {

    @Test
    @DisplayName("Deve criar ObjectMapper com JavaTimeModule")
    void deveCriarObjectMapper() {
        ApplicationConfig config = new ApplicationConfig();
        ObjectMapper mapper = config.objectMapper();
        assertNotNull(mapper);
    }
}
