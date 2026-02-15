package br.com.grupo99.customerservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Veiculo Domain Model Tests")
class VeiculoTest {

    @Test
    @DisplayName("Deve criar veículo com dados válidos")
    void deveCriarVeiculoComDadosValidos() {
        Veiculo veiculo = new Veiculo("ABC1234", "Toyota", "Corolla", 2020);
        assertEquals("ABC1234", veiculo.getPlaca());
        assertEquals("Toyota", veiculo.getMarca());
        assertEquals("Corolla", veiculo.getModelo());
        assertEquals(2020, veiculo.getAno());
    }

    @Test
    @DisplayName("Deve criar veículo com construtor vazio")
    void deveCriarVeiculoComConstrutorVazio() {
        Veiculo veiculo = new Veiculo();
        assertNull(veiculo.getPlaca());
    }

    @Test
    @DisplayName("Deve falhar com placa nula")
    void deveFalharComPlacaNula() {
        assertThrows(IllegalArgumentException.class, () -> new Veiculo(null, "Toyota", "Corolla", 2020));
    }

    @Test
    @DisplayName("Deve falhar com placa vazia")
    void deveFalharComPlacaVazia() {
        assertThrows(IllegalArgumentException.class, () -> new Veiculo("", "Toyota", "Corolla", 2020));
    }

    @Test
    @DisplayName("Deve falhar com marca nula")
    void deveFalharComMarcaNula() {
        assertThrows(IllegalArgumentException.class, () -> new Veiculo("ABC1234", null, "Corolla", 2020));
    }

    @Test
    @DisplayName("Deve falhar com marca vazia")
    void deveFalharComMarcaVazia() {
        assertThrows(IllegalArgumentException.class, () -> new Veiculo("ABC1234", " ", "Corolla", 2020));
    }

    @Test
    @DisplayName("Deve falhar com modelo nulo")
    void deveFalharComModeloNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Veiculo("ABC1234", "Toyota", null, 2020));
    }

    @Test
    @DisplayName("Deve falhar com modelo vazio")
    void deveFalharComModeloVazio() {
        assertThrows(IllegalArgumentException.class, () -> new Veiculo("ABC1234", "Toyota", "", 2020));
    }

    @Test
    @DisplayName("Deve falhar com ano nulo")
    void deveFalharComAnoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Veiculo("ABC1234", "Toyota", "Corolla", null));
    }

    @Test
    @DisplayName("Deve falhar com ano inválido baixo")
    void deveFalharComAnoInvalidoBaixo() {
        assertThrows(IllegalArgumentException.class, () -> new Veiculo("ABC1234", "Toyota", "Corolla", 1800));
    }

    @Test
    @DisplayName("Deve falhar com ano inválido alto")
    void deveFalharComAnoInvalidoAlto() {
        assertThrows(IllegalArgumentException.class, () -> new Veiculo("ABC1234", "Toyota", "Corolla", 2200));
    }

    @Test
    @DisplayName("Deve setar e obter campos opcionais")
    void deveSetarCamposOpcionais() {
        Veiculo veiculo = new Veiculo("ABC1234", "Toyota", "Corolla", 2020);
        veiculo.setRenavam("12345678901");
        veiculo.setCor("Branco");
        veiculo.setChassi("XXXXXXXXXXXXXXXXX");
        UUID id = UUID.randomUUID();
        veiculo.setId(id);

        assertEquals("12345678901", veiculo.getRenavam());
        assertEquals("Branco", veiculo.getCor());
        assertEquals("XXXXXXXXXXXXXXXXX", veiculo.getChassi());
        assertEquals(id, veiculo.getId());
    }

    @Test
    @DisplayName("Deve setar e obter todos os campos")
    void deveSetarTodosCampos() {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("XYZ9876");
        veiculo.setMarca("Honda");
        veiculo.setModelo("Civic");
        veiculo.setAno(2022);

        assertEquals("XYZ9876", veiculo.getPlaca());
        assertEquals("Honda", veiculo.getMarca());
        assertEquals("Civic", veiculo.getModelo());
        assertEquals(2022, veiculo.getAno());
    }

    @Test
    @DisplayName("Deve setar e obter cliente")
    void deveSetarCliente() {
        Veiculo veiculo = new Veiculo("ABC1234", "Toyota", "Corolla", 2020);
        Cliente cliente = new Cliente(UUID.randomUUID());
        veiculo.setCliente(cliente);
        assertEquals(cliente, veiculo.getCliente());
    }

    @Test
    @DisplayName("Deve testar equals e hashCode")
    void deveTestarEqualsEHashCode() {
        Veiculo v1 = new Veiculo("ABC1234", "Toyota", "Corolla", 2020);
        Veiculo v2 = new Veiculo("XYZ9876", "Honda", "Civic", 2022);
        UUID id = UUID.randomUUID();
        try {
            var idField = Veiculo.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(v1, id);
            idField.set(v2, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(v1, v2);
        assertEquals(v1.hashCode(), v2.hashCode());
        assertEquals(v1, v1);
        assertNotEquals(v1, null);
        assertNotEquals(v1, "string");
    }

    @Test
    @DisplayName("Deve testar toString")
    void deveTestarToString() {
        Veiculo veiculo = new Veiculo("ABC1234", "Toyota", "Corolla", 2020);
        String toString = veiculo.toString();
        assertTrue(toString.contains("placa='ABC1234'"));
        assertTrue(toString.contains("marca='Toyota'"));
        assertTrue(toString.contains("modelo='Corolla'"));
        assertTrue(toString.contains("ano=2020"));
    }
}
