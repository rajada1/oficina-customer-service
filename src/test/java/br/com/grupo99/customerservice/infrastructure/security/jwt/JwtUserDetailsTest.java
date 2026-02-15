package br.com.grupo99.customerservice.infrastructure.security.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtUserDetails Tests")
class JwtUserDetailsTest {

    private static final UUID PESSOA_ID = UUID.randomUUID();
    private static final String USERNAME = "testuser";
    private static final String NUMERO_DOCUMENTO = "12345678901";
    private static final String TIPO_PESSOA = "FISICA";
    private static final String CARGO = "MECANICO";
    private static final String PERFIL = "ADMIN";

    @Test
    @DisplayName("Deve criar JwtUserDetails com from")
    void deveCriarComFrom() {
        JwtUserDetails details = JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, PERFIL);

        assertEquals(USERNAME, details.getUsername());
        assertEquals(PESSOA_ID, details.getPessoaId());
        assertEquals(NUMERO_DOCUMENTO, details.getNumeroDocumento());
        assertEquals(TIPO_PESSOA, details.getTipoPessoa());
        assertEquals(CARGO, details.getCargo());
        assertEquals(PERFIL, details.getPerfil());
        assertNull(details.getPassword());
        assertFalse(details.getAuthorities().isEmpty());
        assertTrue(details.isAccountNonExpired());
        assertTrue(details.isAccountNonLocked());
        assertTrue(details.isCredentialsNonExpired());
        assertTrue(details.isEnabled());
    }

    @Test
    @DisplayName("Deve criar JwtUserDetails com withPassword")
    void deveCriarComWithPassword() {
        JwtUserDetails details = JwtUserDetails.withPassword(USERNAME, "password", PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, PERFIL);

        assertEquals(USERNAME, details.getUsername());
        assertEquals("password", details.getPassword());
        assertEquals(PESSOA_ID, details.getPessoaId());
    }

    @Test
    @DisplayName("Deve criar JwtUserDetails com construtor vazio")
    void deveCriarComConstrutorVazio() {
        JwtUserDetails details = new JwtUserDetails();
        assertEquals("", details.getUsername());
        assertNull(details.getPassword());
        assertNull(details.getPessoaId());
        assertTrue(details.getAuthorities().isEmpty());
    }

    @Test
    @DisplayName("Deve falhar com username nulo no from")
    void deveFalharComUsernameNuloNoFrom() {
        assertThrows(NullPointerException.class, () ->
                JwtUserDetails.from(null, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, PERFIL));
    }

    @Test
    @DisplayName("Deve falhar com pessoaId nulo no from")
    void deveFalharComPessoaIdNuloNoFrom() {
        assertThrows(NullPointerException.class, () ->
                JwtUserDetails.from(USERNAME, null, NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, PERFIL));
    }

    @Test
    @DisplayName("Deve falhar com pessoaId inválido")
    void deveFalharComPessoaIdInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
                JwtUserDetails.from(USERNAME, "invalid-uuid", NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, PERFIL));
    }

    @Test
    @DisplayName("Deve falhar com numeroDocumento nulo")
    void deveFalharComNumeroDocumentoNulo() {
        assertThrows(NullPointerException.class, () ->
                JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), null, TIPO_PESSOA, CARGO, PERFIL));
    }

    @Test
    @DisplayName("Deve falhar com tipoPessoa nulo")
    void deveFalharComTipoPessoaNulo() {
        assertThrows(NullPointerException.class, () ->
                JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, null, CARGO, PERFIL));
    }

    @Test
    @DisplayName("Deve falhar com perfil nulo")
    void deveFalharComPerfilNulo() {
        assertThrows(NullPointerException.class, () ->
                JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, null));
    }

    @Test
    @DisplayName("Deve falhar com withPassword username nulo")
    void deveFalharComWithPasswordUsernameNulo() {
        assertThrows(NullPointerException.class, () ->
                JwtUserDetails.withPassword(null, "pass", PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, PERFIL));
    }

    @Test
    @DisplayName("Deve falhar com withPassword password nulo")
    void deveFalharComWithPasswordPasswordNulo() {
        assertThrows(NullPointerException.class, () ->
                JwtUserDetails.withPassword(USERNAME, null, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, PERFIL));
    }

    @Test
    @DisplayName("Deve falhar com withPassword pessoaId inválido")
    void deveFalharComWithPasswordPessoaIdInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
                JwtUserDetails.withPassword(USERNAME, "pass", "not-a-uuid", NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, PERFIL));
    }

    @Test
    @DisplayName("isMecanicoOrHigher deve retornar true para MECANICO")
    void isMecanicoOrHigherParaMecanico() {
        JwtUserDetails details = JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, "MECANICO");
        assertTrue(details.isMecanicoOrHigher());
    }

    @Test
    @DisplayName("isMecanicoOrHigher deve retornar true para ADMIN")
    void isMecanicoOrHigherParaAdmin() {
        JwtUserDetails details = JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, "ADMIN");
        assertTrue(details.isMecanicoOrHigher());
    }

    @Test
    @DisplayName("isMecanicoOrHigher deve retornar false para CLIENTE")
    void isMecanicoOrHigherParaCliente() {
        JwtUserDetails details = JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, "CLIENTE");
        assertFalse(details.isMecanicoOrHigher());
    }

    @Test
    @DisplayName("isCliente deve retornar true para CLIENTE")
    void isClienteParaCliente() {
        JwtUserDetails details = JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, "CLIENTE");
        assertTrue(details.isCliente());
    }

    @Test
    @DisplayName("isCliente deve retornar false para ADMIN")
    void isClienteParaAdmin() {
        JwtUserDetails details = JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, "ADMIN");
        assertFalse(details.isCliente());
    }

    @Test
    @DisplayName("isOwnerOrMecanico para ADMIN deve retornar true")
    void isOwnerOrMecanicoParaAdmin() {
        JwtUserDetails details = JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, "ADMIN");
        assertTrue(details.isOwnerOrMecanico(UUID.randomUUID()));
    }

    @Test
    @DisplayName("isOwnerOrMecanico para dono deve retornar true")
    void isOwnerOrMecanicoParaDono() {
        JwtUserDetails details = JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, "CLIENTE");
        assertTrue(details.isOwnerOrMecanico(PESSOA_ID));
    }

    @Test
    @DisplayName("isOwnerOrMecanico para outro cliente deve retornar false")
    void isOwnerOrMecanicoParaOutroCliente() {
        JwtUserDetails details = JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, "CLIENTE");
        assertFalse(details.isOwnerOrMecanico(UUID.randomUUID()));
    }

    @Test
    @DisplayName("isOwnerOrMecanico sem pessoaId deve retornar false")
    void isOwnerOrMecanicoSemPessoaId() {
        JwtUserDetails details = new JwtUserDetails();
        assertFalse(details.isOwnerOrMecanico(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Deve testar equals e hashCode")
    void deveTestarEqualsEHashCode() {
        JwtUserDetails d1 = JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, PERFIL);
        JwtUserDetails d2 = JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, PERFIL);
        JwtUserDetails d3 = JwtUserDetails.from("other", PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, PERFIL);

        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
        assertNotEquals(d1, d3);
        assertEquals(d1, d1);
        assertNotEquals(d1, null);
        assertNotEquals(d1, "string");
    }

    @Test
    @DisplayName("Deve testar toString")
    void deveTestarToString() {
        JwtUserDetails details = JwtUserDetails.from(USERNAME, PESSOA_ID.toString(), NUMERO_DOCUMENTO, TIPO_PESSOA, CARGO, PERFIL);
        String str = details.toString();
        assertTrue(str.contains("username='" + USERNAME + "'"));
        assertTrue(str.contains("pessoaId=" + PESSOA_ID));
    }
}
