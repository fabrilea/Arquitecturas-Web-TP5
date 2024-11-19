package edu.tudai.microserviciofactura.test;

import edu.tudai.microserviciofactura.controller.DetalleFacturaController;
import edu.tudai.microserviciofactura.entity.DetalleFactura;
import edu.tudai.microserviciofactura.service.DetalleFacturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DetalleFacturaControllerTest {

    @Mock
    private DetalleFacturaService detalleFacturaService;

    @InjectMocks
    private DetalleFacturaController detalleFacturaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDetalles() {
        when(detalleFacturaService.findAll()).thenReturn(List.of(new DetalleFactura()));
        ResponseEntity<List<DetalleFactura>> response = detalleFacturaController.getAllDetalles();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetDetalleById() {
        DetalleFactura detalle = new DetalleFactura();
        detalle.setId(1L);
        when(detalleFacturaService.findById(1L)).thenReturn(detalle);

        ResponseEntity<DetalleFactura> response = detalleFacturaController.getDetalleById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testCreateDetalle() {
        DetalleFactura detalle = new DetalleFactura();
        when(detalleFacturaService.save(detalle)).thenReturn(detalle);

        ResponseEntity<DetalleFactura> response = detalleFacturaController.createDetalle(detalle);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateDetalle() {
        DetalleFactura detalle = new DetalleFactura();
        detalle.setId(1L);
        when(detalleFacturaService.update(detalle)).thenReturn(detalle);

        ResponseEntity<DetalleFactura> response = detalleFacturaController.updateDetalle(1L, detalle);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteDetalle() {
        doNothing().when(detalleFacturaService).delete(1L);
        ResponseEntity<Void> response = detalleFacturaController.deleteDetalle(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
