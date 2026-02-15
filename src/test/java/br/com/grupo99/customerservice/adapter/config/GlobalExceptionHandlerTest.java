package br.com.grupo99.customerservice.adapter.config;

import br.com.grupo99.customerservice.application.exception.BusinessException;
import br.com.grupo99.customerservice.application.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Deve tratar ResourceNotFoundException")
    void deveTratarResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Cliente não encontrado");
        ResponseEntity<Map<String, Object>> response = handler.handleResourceNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().get("status"));
        assertEquals("Not Found", response.getBody().get("error"));
        assertEquals("Cliente não encontrado", response.getBody().get("message"));
    }

    @Test
    @DisplayName("Deve tratar BusinessException")
    void deveTratarBusinessException() {
        BusinessException ex = new BusinessException("Erro de negócio");
        ResponseEntity<Map<String, Object>> response = handler.handleBusinessException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().get("status"));
        assertEquals("Business Error", response.getBody().get("error"));
        assertEquals("Erro de negócio", response.getBody().get("message"));
    }

    @Test
    @DisplayName("Deve tratar Exception genérica")
    void deveTratarExceptionGenerica() {
        Exception ex = new Exception("Erro inesperado");
        ResponseEntity<Map<String, Object>> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().get("status"));
        assertEquals("Internal Server Error", response.getBody().get("error"));
        assertEquals("Erro interno do servidor", response.getBody().get("message"));
    }
}
