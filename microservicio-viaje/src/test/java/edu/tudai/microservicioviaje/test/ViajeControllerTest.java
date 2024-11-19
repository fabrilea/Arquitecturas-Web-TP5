package edu.tudai.microservicioviaje.test;

import edu.tudai.microservicioviaje.controller.ViajeController;
import edu.tudai.microservicioviaje.entity.Viaje;
import edu.tudai.microservicioviaje.entity.Pausa;
import edu.tudai.microservicioviaje.service.ViajeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViajeControllerTest {

    @Mock
    private ViajeService viajeService;

    @InjectMocks
    private ViajeController viajeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllViajes() {
        when(viajeService.findAll()).thenReturn(List.of(new Viaje()));
        ResponseEntity<List<Viaje>> response = viajeController.getAllViajes();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetViajeById() {
        Viaje viaje = new Viaje();
        viaje.setId(1L);
        when(viajeService.findById(1L)).thenReturn(viaje);

        ResponseEntity<Viaje> response = viajeController.getViajeById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testCreateViaje() {
        Viaje viaje = new Viaje();
        when(viajeService.save(viaje)).thenReturn(viaje);

        ResponseEntity<Viaje> response = viajeController.createViaje(viaje);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateViaje() {
        Viaje viaje = new Viaje();
        viaje.setId(1L);
        when(viajeService.update(viaje)).thenReturn(viaje);

        ResponseEntity<Viaje> response = viajeController.updateViaje(1L, viaje);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testDeleteViaje() {
        doNothing().when(viajeService).delete(1L);
        ResponseEntity<Void> response = viajeController.deleteViaje(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testAgregarPausa() {
        Pausa pausa = new Pausa();
        doNothing().when(viajeService).agregarPausa(1L, pausa);
        ResponseEntity<Void> response = viajeController.agregarPausa(1L, pausa);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testFinalizarViaje() {
        doNothing().when(viajeService).finalizarViaje(1L);
        ResponseEntity<Void> response = viajeController.finalizarViaje(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testObtenerTiempoTotalConPausas() {
        when(viajeService.obtenerTiempoTotalConPausas(1L)).thenReturn(120L);
        ResponseEntity<Long> response = viajeController.obtenerTiempoTotalConPausas(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(120L, response.getBody());
    }
}