package edu.tudai.microserviciousuario.test;

import edu.tudai.microserviciousuario.controller.CuentaController;
import edu.tudai.microserviciousuario.entity.Cuenta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CuentaControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate;

    @Autowired
    public CuentaControllerIntegrationTest(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Test
    void testCrearCuenta() {
        String url = "http://localhost:" + port + "/api/cuentas";
        Cuenta nuevaCuenta = new Cuenta("juan@example.com", "password123");

        ResponseEntity<Cuenta> response = restTemplate.postForEntity(url, nuevaCuenta, Cuenta.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo("juan@example.com");
    }

    @Test
    void testObtenerCuentaPorId() {
        Long cuentaId = 1L;
        String url = "http://localhost:" + port + "/api/cuentas/" + cuentaId;

        ResponseEntity<Cuenta> response = restTemplate.getForEntity(url, Cuenta.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(cuentaId);
    }

    @Test
    void testActualizarCuenta() {
        Long cuentaId = 1L;
        String url = "http://localhost:" + port + "/api/cuentas/" + cuentaId;

        Cuenta cuentaActualizada = new Cuenta("juan@example.com", "newpassword123");
        HttpEntity<Cuenta> requestEntity = new HttpEntity<>(cuentaActualizada);

        ResponseEntity<Cuenta> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Cuenta.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPassword()).isEqualTo("newpassword123");
    }

    @Test
    void testAnularCuenta() {
        Long cuentaId = 1L;
        String url = "http://localhost:" + port + "/api/cuentas/" + cuentaId;

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }
}