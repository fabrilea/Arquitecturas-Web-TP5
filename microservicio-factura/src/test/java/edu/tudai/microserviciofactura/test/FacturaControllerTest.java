package edu.tudai.microserviciofactura.test;

import edu.tudai.microserviciofactura.controller.FacturaController;
import edu.tudai.microserviciofactura.entity.Factura;
import edu.tudai.microserviciofactura.service.FacturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacturaControllerTest {

    @Mock
    private FacturaService facturaService;

    @InjectMocks
    private FacturaController facturaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllFacturas() {
        when(facturaService.findAll()).thenReturn(List.of(new Factura()));
        ResponseEntity<List<Factura>> response = facturaController.getAllFacturas();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetFacturaById() {
        Factura factura = new Factura();
        factura.setId(1L);
        when(facturaService.findById(1L)).thenReturn(factura);

        ResponseEntity<Factura> response = facturaController.getFacturaById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testCreateFactura() {
        Factura factura = new Factura();
        when(facturaService.save(factura)).thenReturn(factura);

        ResponseEntity<Factura> response = facturaController.createFactura(factura);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateFactura() {
        Factura factura = new Factura();
        factura.setId(1L);
        when(facturaService.update(factura)).thenReturn(factura);

        ResponseEntity<Factura> response = facturaController.updateFactura(1L, factura);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testDeleteFactura() {
        doNothing().when(facturaService).delete(1L);
        ResponseEntity<Void> response = facturaController.deleteFactura(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testObtenerTotalFacturado() {
        when(facturaService.obtenerTotalFacturado(2024, 1, 3)).thenReturn(5000.0);
        ResponseEntity<Double> response = facturaController.obtenerTotalFacturado(2024, 1, 3);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(5000.0, response.getBody());
    }
}
