package br.com.grupo99.customerservice.infrastructure.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("JwtAuthorizationFilter Tests")
class JwtAuthorizationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private JwtAuthorizationFilter filter;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Deve continuar sem header Authorization")
    void deveContinuarSemHeader() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);
        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Deve continuar com header sem Bearer")
    void deveContinuarSemBearer() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Basic xyz");
        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Deve continuar sem autenticação")
    void deveContinuarSemAutenticacao() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        // No authentication in context
        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Deve permitir acesso para ADMIN")
    void devePermitirAcessoParaAdmin() throws Exception {
        setupAuthenticatedContext();
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.extractPerfil("token")).thenReturn("ADMIN");
        when(jwtUtil.extractPessoaId("token")).thenReturn(java.util.UUID.randomUUID().toString());

        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Deve permitir acesso para MECANICO")
    void devePermitirAcessoParaMecanico() throws Exception {
        setupAuthenticatedContext();
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.extractPerfil("token")).thenReturn("MECANICO");
        when(jwtUtil.extractPessoaId("token")).thenReturn(java.util.UUID.randomUUID().toString());

        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Deve negar acesso CLIENTE para listar execuções")
    void deveNegarAcessoClienteListarExecucoes() throws Exception {
        setupAuthenticatedContext();
        StringWriter sw = new StringWriter();
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.extractPerfil("token")).thenReturn("CLIENTE");
        when(jwtUtil.extractPessoaId("token")).thenReturn(java.util.UUID.randomUUID().toString());
        when(request.getRequestURI()).thenReturn("/api/v1/execucoes");
        when(response.getWriter()).thenReturn(new PrintWriter(sw));

        filter.doFilterInternal(request, response, filterChain);
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Deve negar acesso CLIENTE para listar execution")
    void deveNegarAcessoClienteListarExecution() throws Exception {
        setupAuthenticatedContext();
        StringWriter sw = new StringWriter();
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.extractPerfil("token")).thenReturn("CLIENTE");
        when(jwtUtil.extractPessoaId("token")).thenReturn(java.util.UUID.randomUUID().toString());
        when(request.getRequestURI()).thenReturn("/api/v1/execution");
        when(response.getWriter()).thenReturn(new PrintWriter(sw));

        filter.doFilterInternal(request, response, filterChain);
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Deve negar acesso CLIENTE para métodos não-GET")
    void deveNegarAcessoClienteMetodoNaoGet() throws Exception {
        setupAuthenticatedContext();
        StringWriter sw = new StringWriter();
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.extractPerfil("token")).thenReturn("CLIENTE");
        when(jwtUtil.extractPessoaId("token")).thenReturn(java.util.UUID.randomUUID().toString());
        when(request.getRequestURI()).thenReturn("/api/v1/something");
        when(request.getMethod()).thenReturn("POST");
        when(response.getWriter()).thenReturn(new PrintWriter(sw));

        filter.doFilterInternal(request, response, filterChain);
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Deve permitir acesso CLIENTE para método GET")
    void devePermitirAcessoClienteMetodoGet() throws Exception {
        setupAuthenticatedContext();
        String pessoaId = java.util.UUID.randomUUID().toString();
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.extractPerfil("token")).thenReturn("CLIENTE");
        when(jwtUtil.extractPessoaId("token")).thenReturn(pessoaId);
        when(request.getRequestURI()).thenReturn("/api/v1/something");
        when(request.getMethod()).thenReturn("GET");

        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
        verify(request).setAttribute("pessoaId", pessoaId);
        verify(request).setAttribute("perfil", "CLIENTE");
    }

    private void setupAuthenticatedContext() {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("user", null, java.util.Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
