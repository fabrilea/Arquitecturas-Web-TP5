package edu.tudai.microservicioadministrador.test;

import edu.tudai.microserviciomantenimiento.dto.ReporteKilometrosDTO;
import edu.tudai.microserviciomantenimiento.entity.Mantenimiento;
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
public class MantenimientoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testIniciarMantenimiento() {
        Long monopatinId = 1L;
        String url = "http://localhost:" + port + "/api/mantenimiento/iniciar/" + monopatinId;

        HttpEntity<String> requestEntity = new HttpEntity<>("Revisión de frenos");

        ResponseEntity<Mantenimiento> response = restTemplate.postForEntity(url, requestEntity, Mantenimiento.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescripcion()).isEqualTo("Revisión de frenos");
    }

    @Test
    void testFinalizarMantenimiento() {
        Long monopatinId = 1L;
        String url = "http://localhost:" + port + "/api/mantenimiento/finalizar/" + monopatinId;

        ResponseEntity<Mantenimiento> response = restTemplate.exchange(url, HttpMethod.PUT, null, Mantenimiento.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFechaFin()).isNotNull();
    }

    @Test
    void testConsultarEstadoMantenimiento() {
        Long monopatinId = 1L;
        String url = "http://localhost:" + port + "/api/mantenimiento/estado/" + monopatinId;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo("En mantenimiento");
    }

    @Test
    void testActualizarMantenimiento() {
        Long mantenimientoId = 1L;
        String url = "http://localhost:" + port + "/api/mantenimiento/actualizar/" + mantenimientoId;

        Mantenimiento mantenimientoActualizado = new Mantenimiento(1L, "Revisión completa", LocalDate.now(), LocalDate.now().plusDays(1));
        HttpEntity<Mantenimiento> requestEntity = new HttpEntity<>(mantenimientoActualizado);

        ResponseEntity<Mantenimiento> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Mantenimiento.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescripcion()).isEqualTo("Revisión completa");
    }

    @Test
    void testGenerarReporteUsoMonopatines() {
        String url = "http://localhost:" + port + "/api/mantenimiento/reporte-uso?incluirPausas=true";

        ResponseEntity<ReporteKilometrosDTO[]> response = restTemplate.getForEntity(url, ReporteKilometrosDTO[].class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotEmpty();
    }
}