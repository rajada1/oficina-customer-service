package br.com.grupo99.customerservice.adapter.repository;

import br.com.grupo99.customerservice.domain.model.Cliente;
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
@DisplayName("ClienteRepositoryAdapter Tests")
class ClienteRepositoryAdapterTest {

    @Mock
    private ClienteJpaRepository jpaRepository;

    @InjectMocks
    private ClienteRepositoryAdapter adapter;

    private UUID pessoaId;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        pessoaId = UUID.randomUUID();
        cliente = new Cliente(pessoaId);
    }

    @Test
    @DisplayName("Deve salvar cliente")
    void deveSalvarCliente() {
        when(jpaRepository.save(cliente)).thenReturn(cliente);
        Cliente result = adapter.save(cliente);
        assertEquals(cliente, result);
        verify(jpaRepository).save(cliente);
    }

    @Test
    @DisplayName("Deve buscar por pessoaId")
    void deveBuscarPorPessoaId() {
        when(jpaRepository.findByPessoaId(pessoaId)).thenReturn(Optional.of(cliente));
        Optional<Cliente> result = adapter.findByPessoaId(pessoaId);
        assertTrue(result.isPresent());
        assertEquals(cliente, result.get());
    }

    @Test
    @DisplayName("Deve verificar existência por pessoaId")
    void deveVerificarExistenciaPorPessoaId() {
        when(jpaRepository.existsByPessoaId(pessoaId)).thenReturn(true);
        assertTrue(adapter.existsByPessoaId(pessoaId));
    }

    @Test
    @DisplayName("Deve deletar por pessoaId")
    void deveDeletarPorPessoaId() {
        adapter.deleteByPessoaId(pessoaId);
        verify(jpaRepository).deleteByPessoaId(pessoaId);
    }

    @Test
    @DisplayName("Deve buscar por id")
    void deveBuscarPorId() {
        when(jpaRepository.findById(pessoaId)).thenReturn(Optional.of(cliente));
        Optional<Cliente> result = adapter.findById(pessoaId);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Deve listar todos")
    void deveListarTodos() {
        when(jpaRepository.findAll()).thenReturn(List.of(cliente));
        List<Cliente> result = adapter.findAll();
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deve deletar por id")
    void deveDeletarPorId() {
        adapter.deleteById(pessoaId);
        verify(jpaRepository).deleteById(pessoaId);
    }
}
