package edu.tudai.microserviciousuario.test;

import edu.tudai.microserviciousuario.controller.UsuarioController;
import edu.tudai.microserviciousuario.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCrearUsuario() {
        String url = "http://localhost:" + port + "/api/usuarios";
        Usuario nuevoUsuario = new Usuario("juan@example.com", "Juan", "password123");

        ResponseEntity<Usuario> response = restTemplate.postForEntity(url, nuevoUsuario, Usuario.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo("juan@example.com");
    }

    @Test
    void testObtenerUsuarioPorId() {
        Long usuarioId = 1L;
        String url = "http://localhost:" + port + "/api/usuarios/" + usuarioId;

        ResponseEntity<Usuario> response = restTemplate.getForEntity(url, Usuario.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(usuarioId);
    }

    @Test
    void testActualizarUsuario() {
        Long usuarioId = 1L;
        String url = "http://localhost:" + port + "/api/usuarios/" + usuarioId;

        Usuario usuarioActualizado = new Usuario("juan@example.com", "Juan Carlos", "password123");
        HttpEntity<Usuario> requestEntity = new HttpEntity<>(usuarioActualizado);

        ResponseEntity<Usuario> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Usuario.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNombre()).isEqualTo("Juan Carlos");
    }

    @Test
    void testEliminarUsuario() {
        Long usuarioId = 1L;
        String url = "http://localhost:" + port + "/api/usuarios/" + usuarioId;

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
