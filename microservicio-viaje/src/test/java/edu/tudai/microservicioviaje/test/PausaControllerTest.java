package edu.tudai.microservicioviaje.test;

import edu.tudai.microservicioviaje.controller.PausaController;
import edu.tudai.microservicioviaje.entity.Pausa;
import edu.tudai.microservicioviaje.service.PausaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PausaControllerTest {

    @Mock
    private PausaService pausaService;

    @InjectMocks
    private PausaController pausaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPausas() {
        when(pausaService.findAll()).thenReturn(List.of(new Pausa()));
        ResponseEntity<List<Pausa>> response = pausaController.getAllPausas();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetPausaById() {
        Pausa pausa = new Pausa();
        pausa.setId(1L);
        when(pausaService.findById(1L)).thenReturn(pausa);

        ResponseEntity<Pausa> response = pausaController.getPausaById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testCreatePausa() {
        Pausa pausa = new Pausa();
        when(pausaService.save(pausa)).thenReturn(pausa);

        ResponseEntity<Pausa> response = pausaController.createPausa(pausa);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdatePausa() {
        Pausa pausa = new Pausa();
        pausa.setId(1L);
        when(pausaService.update(pausa)).thenReturn(pausa);

        ResponseEntity<Pausa> response = pausaController.updatePausa(1L, pausa);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testDeletePausa() {
        doNothing().when(pausaService).delete(1L);
        ResponseEntity<Void> response = pausaController.deletePausa(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
