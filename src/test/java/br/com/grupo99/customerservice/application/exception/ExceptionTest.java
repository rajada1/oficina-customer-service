package br.com.grupo99.customerservice.application.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exception Tests")
class ExceptionTest {

    @Test
    @DisplayName("Deve criar ResourceNotFoundException com mensagem")
    void deveCriarResourceNotFoundExceptionComMensagem() {
        ResourceNotFoundException ex = new ResourceNotFoundException("not found");
        assertEquals("not found", ex.getMessage());
    }

    @Test
    @DisplayName("Deve criar ResourceNotFoundException com mensagem e causa")
    void deveCriarResourceNotFoundExceptionComMensagemECausa() {
        Throwable cause = new RuntimeException("cause");
        ResourceNotFoundException ex = new ResourceNotFoundException("not found", cause);
        assertEquals("not found", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    @DisplayName("Deve criar BusinessException com mensagem")
    void deveCriarBusinessExceptionComMensagem() {
        BusinessException ex = new BusinessException("business error");
        assertEquals("business error", ex.getMessage());
    }

    @Test
    @DisplayName("Deve criar BusinessException com mensagem e causa")
    void deveCriarBusinessExceptionComMensagemECausa() {
        Throwable cause = new RuntimeException("cause");
        BusinessException ex = new BusinessException("business error", cause);
        assertEquals("business error", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
