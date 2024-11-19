package edu.tudai.microservicio_administrador.test;

import edu.tudai.microservicioadministrador.dto.MonopatinDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testObtenerMonopatinesConMasViajes() {
        String url = "http://localhost:" + port + "/admin/monopatinViajes/5/2023";

        ResponseEntity<MonopatinDTO[]> response = restTemplate.getForEntity(url, MonopatinDTO[].class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testAnularCuenta() {
        long cuentaId = 1L;
        String url = "http://localhost:" + port + "/admin/anularCuenta/" + cuentaId;

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, null, Void.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void testMonopatinesVS() {
        String url = "http://localhost:" + port + "/admin/monopatines/comparacion";

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).containsKeys("operativos", "enMantenimiento");
    }

    @Test
    void testObtenerTotalFacturado() {
        String url = "http://localhost:" + port + "/admin/factura/total-facturado?anio=2023&mesInicio=1&mesFin=3";

        ResponseEntity<Double> response = restTemplate.getForEntity(url, Double.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testAjustarPrecios() {
        String url = "http://localhost:" + port + "/admin/tarifa/ajustar-precios?nuevaTarifaBase=10.0&nuevaTarifaExtra=2.0&fechaInicio=2023-01-01";

        HttpEntity<Void> requestEntity = new HttpEntity<>(null);

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }
}