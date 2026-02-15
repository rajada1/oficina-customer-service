package br.com.grupo99.customerservice.domain.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Domain Events Tests")
class DomainEventsTest {

    @Test
    @DisplayName("Deve criar ClienteCriadoEvent")
    void deveCriarClienteCriadoEvent() {
        UUID clienteId = UUID.randomUUID();
        LocalDateTime timestamp = LocalDateTime.now();
        ClienteCriadoEvent event = new ClienteCriadoEvent(clienteId, "João", "12345678901", "joao@email.com", "11999999999", timestamp, "CLIENTE_CRIADO");

        assertEquals(clienteId, event.getClienteId());
        assertEquals("João", event.getNome());
        assertEquals("12345678901", event.getCpf());
        assertEquals("joao@email.com", event.getEmail());
        assertEquals("11999999999", event.getTelefone());
        assertEquals(timestamp, event.getTimestamp());
        assertEquals("CLIENTE_CRIADO", event.getEventType());
    }

    @Test
    @DisplayName("Deve criar ClienteCriadoEvent com construtor vazio")
    void deveCriarClienteCriadoEventComConstrutorVazio() {
        ClienteCriadoEvent event = new ClienteCriadoEvent();
        assertNull(event.getClienteId());
    }

    @Test
    @DisplayName("Deve setar campos do ClienteCriadoEvent")
    void deveSetarCamposClienteCriadoEvent() {
        ClienteCriadoEvent event = new ClienteCriadoEvent();
        UUID id = UUID.randomUUID();
        event.setClienteId(id);
        event.setNome("Maria");
        event.setCpf("98765432101");
        event.setEmail("maria@email.com");
        event.setTelefone("11888888888");
        event.setTimestamp(LocalDateTime.now());
        event.setEventType("CLIENTE_CRIADO");

        assertEquals(id, event.getClienteId());
        assertEquals("Maria", event.getNome());
    }

    @Test
    @DisplayName("Deve criar VeiculoAdicionadoEvent")
    void deveCriarVeiculoAdicionadoEvent() {
        UUID veiculoId = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();
        LocalDateTime timestamp = LocalDateTime.now();
        VeiculoAdicionadoEvent event = new VeiculoAdicionadoEvent(veiculoId, clienteId, "ABC1234", "Toyota", "Corolla", 2020, timestamp, "VEICULO_ADICIONADO");

        assertEquals(veiculoId, event.getVeiculoId());
        assertEquals(clienteId, event.getClienteId());
        assertEquals("ABC1234", event.getPlaca());
        assertEquals("Toyota", event.getMarca());
        assertEquals("Corolla", event.getModelo());
        assertEquals(2020, event.getAno());
        assertEquals(timestamp, event.getTimestamp());
        assertEquals("VEICULO_ADICIONADO", event.getEventType());
    }

    @Test
    @DisplayName("Deve criar VeiculoAdicionadoEvent com construtor vazio")
    void deveCriarVeiculoAdicionadoEventComConstrutorVazio() {
        VeiculoAdicionadoEvent event = new VeiculoAdicionadoEvent();
        assertNull(event.getVeiculoId());
    }

    @Test
    @DisplayName("Deve setar campos do VeiculoAdicionadoEvent")
    void deveSetarCamposVeiculoAdicionadoEvent() {
        VeiculoAdicionadoEvent event = new VeiculoAdicionadoEvent();
        UUID veiculoId = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();
        event.setVeiculoId(veiculoId);
        event.setClienteId(clienteId);
        event.setPlaca("XYZ9876");
        event.setMarca("Honda");
        event.setModelo("Civic");
        event.setAno(2022);
        event.setTimestamp(LocalDateTime.now());
        event.setEventType("VEICULO_ADICIONADO");

        assertEquals(veiculoId, event.getVeiculoId());
        assertEquals(clienteId, event.getClienteId());
        assertEquals("XYZ9876", event.getPlaca());
    }
}
