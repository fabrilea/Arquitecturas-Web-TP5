package edu.tudai.microserviciomonopatin.test;

import edu.tudai.microserviciomonopatin.controller.ParadaController;
import edu.tudai.microserviciomonopatin.entity.Parada;
import edu.tudai.microserviciomonopatin.service.ParadaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParadaControllerTest {

    @Mock
    private ParadaService paradaService;

    @InjectMocks
    private ParadaController paradaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllParadas() {
        when(paradaService.findAll()).thenReturn(List.of(new Parada()));
        ResponseEntity<List<Parada>> response = paradaController.getAllParadas();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetParadaById() {
        Parada parada = new Parada();
        parada.setId(1L);
        when(paradaService.findById(1L)).thenReturn(parada);

        ResponseEntity<Parada> response = paradaController.getParadaById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testCreateParada() {
        Parada parada = new Parada();
        when(paradaService.save(parada)).thenReturn(parada);

        ResponseEntity<Parada> response = paradaController.createParada(parada);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testDeleteParada() {
        doNothing().when(paradaService).delete(1L);
        ResponseEntity<Void> response = paradaController.deleteParada(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testUpdateParada() {
        Parada parada = new Parada();
        parada.setId(1L);
        when(paradaService.update(parada)).thenReturn(parada);

        ResponseEntity<Parada> response = paradaController.updateParada(1L, parada);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }
}