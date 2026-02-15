package br.com.grupo99.customerservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cliente Domain Model Tests")
class ClienteTest {

    @Test
    @DisplayName("Deve criar cliente com pessoaId válido")
    void deveCriarClienteComPessoaIdValido() {
        UUID pessoaId = UUID.randomUUID();
        Cliente cliente = new Cliente(pessoaId);
        assertEquals(pessoaId, cliente.getPessoaId());
        assertNotNull(cliente.getVeiculos());
        assertTrue(cliente.getVeiculos().isEmpty());
    }

    @Test
    @DisplayName("Deve falhar ao criar cliente com pessoaId nulo")
    void deveFalharCriarClienteComPessoaIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente(null));
    }

    @Test
    @DisplayName("Deve criar cliente com construtor vazio")
    void deveCriarClienteComConstrutorVazio() {
        Cliente cliente = new Cliente();
        assertNull(cliente.getPessoaId());
    }

    @Test
    @DisplayName("Deve adicionar veículo ao cliente")
    void deveAdicionarVeiculoAoCliente() {
        UUID pessoaId = UUID.randomUUID();
        Cliente cliente = new Cliente(pessoaId);
        Veiculo veiculo = new Veiculo("ABC1234", "Toyota", "Corolla", 2020);

        cliente.adicionarVeiculo(veiculo);

        assertEquals(1, cliente.getVeiculos().size());
        assertEquals(cliente, veiculo.getCliente());
    }

    @Test
    @DisplayName("Deve falhar ao adicionar veículo nulo")
    void deveFalharAdicionarVeiculoNulo() {
        Cliente cliente = new Cliente(UUID.randomUUID());
        assertThrows(IllegalArgumentException.class, () -> cliente.adicionarVeiculo(null));
    }

    @Test
    @DisplayName("Deve remover veículo do cliente")
    void deveRemoverVeiculoDoCliente() {
        UUID pessoaId = UUID.randomUUID();
        Cliente cliente = new Cliente(pessoaId);
        Veiculo veiculo = new Veiculo("ABC1234", "Toyota", "Corolla", 2020);
        cliente.adicionarVeiculo(veiculo);

        cliente.removerVeiculo(veiculo);

        assertTrue(cliente.getVeiculos().isEmpty());
        assertNull(veiculo.getCliente());
    }

    @Test
    @DisplayName("Deve ignorar remoção de veículo nulo")
    void deveIgnorarRemocaoVeiculoNulo() {
        Cliente cliente = new Cliente(UUID.randomUUID());
        assertDoesNotThrow(() -> cliente.removerVeiculo(null));
    }

    @Test
    @DisplayName("Deve setar pessoaId válido")
    void deveSetarPessoaIdValido() {
        Cliente cliente = new Cliente();
        UUID pessoaId = UUID.randomUUID();
        cliente.setPessoaId(pessoaId);
        assertEquals(pessoaId, cliente.getPessoaId());
    }

    @Test
    @DisplayName("Deve falhar ao setar pessoaId nulo")
    void deveFalharSetarPessoaIdNulo() {
        Cliente cliente = new Cliente(UUID.randomUUID());
        assertThrows(IllegalArgumentException.class, () -> cliente.setPessoaId(null));
    }

    @Test
    @DisplayName("Deve setar veiculos")
    void deveSetarVeiculos() {
        Cliente cliente = new Cliente(UUID.randomUUID());
        java.util.List<Veiculo> veiculos = new java.util.ArrayList<>();
        veiculos.add(new Veiculo("ABC1234", "Toyota", "Corolla", 2020));
        cliente.setVeiculos(veiculos);
        assertEquals(1, cliente.getVeiculos().size());
    }

    @Test
    @DisplayName("Deve testar equals e hashCode")
    void deveTestarEqualsEHashCode() {
        UUID pessoaId = UUID.randomUUID();
        Cliente cliente1 = new Cliente(pessoaId);
        Cliente cliente2 = new Cliente(pessoaId);
        Cliente cliente3 = new Cliente(UUID.randomUUID());

        assertEquals(cliente1, cliente2);
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
        assertNotEquals(cliente1, cliente3);
        assertNotEquals(cliente1, null);
        assertNotEquals(cliente1, "string");
        assertEquals(cliente1, cliente1);
    }

    @Test
    @DisplayName("Deve testar toString")
    void deveTestarToString() {
        UUID pessoaId = UUID.randomUUID();
        Cliente cliente = new Cliente(pessoaId);
        String toString = cliente.toString();
        assertTrue(toString.contains("pessoaId=" + pessoaId));
        assertTrue(toString.contains("veiculosCount=0"));
    }

    @Test
    @DisplayName("Deve testar onCreate e onUpdate via reflection")
    void deveTestarOnCreateEOnUpdate() throws Exception {
        Cliente cliente = new Cliente(UUID.randomUUID());

        var onCreateMethod = Cliente.class.getDeclaredMethod("onCreate");
        onCreateMethod.setAccessible(true);
        onCreateMethod.invoke(cliente);
        assertNotNull(cliente.getCreatedAt());
        assertNotNull(cliente.getUpdatedAt());

        var onUpdateMethod = Cliente.class.getDeclaredMethod("onUpdate");
        onUpdateMethod.setAccessible(true);
        onUpdateMethod.invoke(cliente);
        assertNotNull(cliente.getUpdatedAt());
    }
}
