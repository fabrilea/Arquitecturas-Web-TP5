package edu.tudai.microserviciofactura.test;

import edu.tudai.microserviciofactura.entity.Factura;
import edu.tudai.microserviciofactura.repository.FacturaRepository;
import edu.tudai.microserviciofactura.service.FacturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private FacturaService facturaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(facturaRepository.findAll()).thenReturn(List.of(new Factura()));
        List<Factura> result = facturaService.findAll();
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindById() {
        Factura factura = new Factura();
        factura.setId(1L);
        when(facturaRepository.findById(1L)).thenReturn(Optional.of(factura));

        Factura result = facturaService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSave() {
        Factura factura = new Factura();
        when(facturaRepository.save(factura)).thenReturn(factura);

        Factura result = facturaService.save(factura);
        assertNotNull(result);
    }

    @Test
    void testDelete() {
        doNothing().when(facturaRepository).deleteById(1L);
        facturaService.delete(1L);
        verify(facturaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdate() {
        Factura factura = new Factura();
        factura.setId(1L);
        when(facturaRepository.save(factura)).thenReturn(factura);

        Factura result = facturaService.update(factura);
        assertEquals(1L, result.getId());
    }

    @Test
    void testObtenerTotalFacturado() {
        when(facturaRepository.totalFacturado(2024, 1, 3)).thenReturn(5000.0);

        double total = facturaService.obtenerTotalFacturado(2024, 1, 3);
        assertEquals(5000.0, total);
    }
}