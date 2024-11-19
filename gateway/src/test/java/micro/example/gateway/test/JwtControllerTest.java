package micro.example.gateway.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import micro.example.gateway.controller.JwtController;
import micro.example.gateway.security.jwt.TokenProvider;
import micro.example.gateway.service.dto.login.LoginDTO;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtControllerTest {

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private ReactiveAuthenticationManager reactiveAuthenticationManager;

    @InjectMocks
    private JwtController jwtController;

    @Test
    void testAuthorizeSuccess() {
        // Datos de prueba
        String username = "testUser";
        String password = "password";
        String token = "dummyJwtToken";

        LoginDTO loginDTO = new LoginDTO(username, password);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        when(reactiveAuthenticationManager.authenticate(any()))
                .thenReturn(Mono.just(authentication));
        when(tokenProvider.createToken(authentication)).thenReturn(token);

        // Ejecución del método
        StepVerifier.create(jwtController.authorize(loginDTO))
                .consumeNextWith(response -> {
                    assertEquals(HttpStatus.OK, response.getStatusCode());
                    assertNotNull(response.getBody());
                    assertEquals(token, response.getBody().getIdToken());
                })
                .verifyComplete();
    }

    @Test
    void testAuthorizeFailure() {
        // Datos de prueba
        LoginDTO loginDTO = new LoginDTO("testUser", "wrongPassword");

        when(reactiveAuthenticationManager.authenticate(any()))
                .thenReturn(Mono.error(new RuntimeException("Authentication failed")));

        // Ejecución del método
        StepVerifier.create(jwtController.authorize(loginDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Authentication failed"))
                .verify();
    }
}
