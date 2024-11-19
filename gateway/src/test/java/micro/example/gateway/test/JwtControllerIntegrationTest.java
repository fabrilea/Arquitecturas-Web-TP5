package micro.example.gateway.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import micro.example.gateway.security.jwt.TokenProvider;
import micro.example.gateway.service.dto.login.LoginDTO;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class JwtControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private ReactiveAuthenticationManager reactiveAuthenticationManager;

    @Test
    void testAuthorizeIntegration() throws Exception {
        // Datos de prueba
        String username = "testUser";
        String password = "password";
        String token = "dummyJwtToken";

        LoginDTO loginDTO = new LoginDTO(username, password);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        when(reactiveAuthenticationManager.authenticate(any()))
                .thenReturn(Mono.just(authentication));
        when(tokenProvider.createToken(authentication)).thenReturn(token);

        mockMvc.perform(post("/api/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_token").value(token));
    }
}
