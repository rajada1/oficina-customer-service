package br.com.grupo99.customerservice.adapter.repository;

import br.com.grupo99.customerservice.domain.model.Veiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VeiculoRepositoryAdapter Tests")
class VeiculoRepositoryAdapterTest {

    @Mock
    private VeiculoJpaRepository jpaRepository;

    @InjectMocks
    private VeiculoRepositoryAdapter adapter;

    private UUID veiculoId;
    private Veiculo veiculo;

    @BeforeEach
    void setUp() {
        veiculoId = UUID.randomUUID();
        veiculo = new Veiculo("ABC1234", "Toyota", "Corolla", 2020);
    }

    @Test
    @DisplayName("Deve salvar veículo")
    void deveSalvarVeiculo() {
        when(jpaRepository.save(veiculo)).thenReturn(veiculo);
        Veiculo result = adapter.save(veiculo);
        assertEquals(veiculo, result);
    }

    @Test
    @DisplayName("Deve buscar por id")
    void deveBuscarPorId() {
        when(jpaRepository.findById(veiculoId)).thenReturn(Optional.of(veiculo));
        Optional<Veiculo> result = adapter.findById(veiculoId);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Deve buscar por placa")
    void deveBuscarPorPlaca() {
        when(jpaRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(veiculo));
        Optional<Veiculo> result = adapter.findByPlaca("ABC1234");
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Deve buscar por cliente pessoaId")
    void deveBuscarPorClientePessoaId() {
        UUID clienteId = UUID.randomUUID();
        when(jpaRepository.findByClientePessoaId(clienteId)).thenReturn(List.of(veiculo));
        List<Veiculo> result = adapter.findByClientePessoaId(clienteId);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deve verificar existência por placa")
    void deveVerificarExistenciaPorPlaca() {
        when(jpaRepository.existsByPlaca("ABC1234")).thenReturn(true);
        assertTrue(adapter.existsByPlaca("ABC1234"));
    }

    @Test
    @DisplayName("Deve deletar por id")
    void deveDeletarPorId() {
        adapter.deleteById(veiculoId);
        verify(jpaRepository).deleteById(veiculoId);
    }

    @Test
    @DisplayName("Deve listar todos")
    void deveListarTodos() {
        when(jpaRepository.findAll()).thenReturn(List.of(veiculo));
        List<Veiculo> result = adapter.findAll();
        assertEquals(1, result.size());
    }
}
