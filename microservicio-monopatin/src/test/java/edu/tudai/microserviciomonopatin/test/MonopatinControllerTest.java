package edu.tudai.microserviciomonopatin.test;

import edu.tudai.microserviciomonopatin.controller.MonopatinController;
import edu.tudai.microserviciomonopatin.entity.Monopatin;
import edu.tudai.microserviciomonopatin.service.MonopatinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonopatinControllerTest {

    @Mock
    private MonopatinService monopatinService;

    @InjectMocks
    private MonopatinController monopatinController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMonopatines() {
        when(monopatinService.findAll()).thenReturn(List.of(new Monopatin()));
        ResponseEntity<List<Monopatin>> response = monopatinController.getAllMonopatines();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetMonopatinById() {
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);
        when(monopatinService.findById(1L)).thenReturn(monopatin);

        ResponseEntity<Monopatin> response = monopatinController.getMonopatinById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testCreateMonopatin() {
        Monopatin monopatin = new Monopatin();
        when(monopatinService.save(monopatin)).thenReturn(monopatin);

        ResponseEntity<Monopatin> response = monopatinController.createMonopatin(monopatin);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testDeleteMonopatin() {
        doNothing().when(monopatinService).delete(1L);
        ResponseEntity<Void> response = monopatinController.deleteMonopatin(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testUpdateMonopatin() {
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);
        when(monopatinService.update(monopatin)).thenReturn(monopatin);

        ResponseEntity<Monopatin> response = monopatinController.updateMonopatin(1L, monopatin);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testObtenerEstadoMonopatines() {
        when(monopatinService.obtenerEstadoMonopatines()).thenReturn(Map.of("DISPONIBLE", 5L));
        ResponseEntity<Map<String, Long>> response = monopatinController.obtenerEstadoMonopatines();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(5L, response.getBody().get("DISPONIBLE"));
    }

    @Test
    void testObtenerMonopatinesCercanos() {
        when(monopatinService.obtenerMonopatinesCercanos(40.0, -74.0, 10.0))
                .thenReturn(List.of(new Monopatin()));
        ResponseEntity<List<Monopatin>> response = monopatinController.obtenerMonopatinesCercanos(40.0, -74.0, 10.0);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }
}
