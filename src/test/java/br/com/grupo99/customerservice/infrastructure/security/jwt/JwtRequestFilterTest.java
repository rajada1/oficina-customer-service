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
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("JwtRequestFilter Tests")
class JwtRequestFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private JwtRequestFilter filter;

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
    @DisplayName("Deve continuar filtro sem header Authorization")
    void deveContinuarSemHeaderAuth() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);
        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Deve continuar filtro com header sem Bearer")
    void deveContinuarComHeaderSemBearer() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Basic xxx");
        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Deve autenticar com token válido")
    void deveAutenticarComTokenValido() throws Exception {
        String token = "valid-token";
        JwtUserDetails userDetails = JwtUserDetails.from("user", java.util.UUID.randomUUID().toString(), "12345678901", "FISICA", "MECANICO", "ADMIN");
        
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUserDetails(token)).thenReturn(userDetails);
        when(jwtUtil.isTokenValid(token, userDetails)).thenReturn(true);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtUtil).isTokenValid(token, userDetails);
    }

    @Test
    @DisplayName("Deve limpar contexto com token inválido")
    void deveLimparContextoComTokenInvalido() throws Exception {
        String token = "invalid-token";
        JwtUserDetails userDetails = JwtUserDetails.from("user", java.util.UUID.randomUUID().toString(), "12345678901", "FISICA", "MECANICO", "ADMIN");
        
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUserDetails(token)).thenReturn(userDetails);
        when(jwtUtil.isTokenValid(token, userDetails)).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Deve tratar IllegalArgumentException")
    void deveTratarIllegalArgumentException() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer bad-token");
        when(jwtUtil.extractUserDetails("bad-token")).thenThrow(new IllegalArgumentException("invalid"));

        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Deve tratar exceção genérica")
    void deveTratarExcecaoGenerica() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer bad-token");
        when(jwtUtil.extractUserDetails("bad-token")).thenThrow(new RuntimeException("error"));

        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }
}
