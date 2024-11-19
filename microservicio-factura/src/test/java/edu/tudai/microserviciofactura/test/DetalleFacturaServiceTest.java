package edu.tudai.microserviciofactura.test;

import edu.tudai.microserviciofactura.entity.DetalleFactura;
import edu.tudai.microserviciofactura.repository.DetalleFacturaRepository;
import edu.tudai.microserviciofactura.service.DetalleFacturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DetalleFacturaServiceTest {

    @Mock
    private DetalleFacturaRepository detalleFacturaRepository;

    @InjectMocks
    private DetalleFacturaService detalleFacturaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(detalleFacturaRepository.findAll()).thenReturn(List.of(new DetalleFactura()));
        assertFalse(detalleFacturaService.findAll().isEmpty());
    }

    @Test
    void testFindById() {
        DetalleFactura detalle = new DetalleFactura();
        detalle.setId(1L);
        when(detalleFacturaRepository.findById(1L)).thenReturn(Optional.of(detalle));

        assertNotNull(detalleFacturaService.findById(1L));
    }

    @Test
    void testSave() {
        DetalleFactura detalle = new DetalleFactura();
        when(detalleFacturaRepository.save(detalle)).thenReturn(detalle);

        assertNotNull(detalleFacturaService.save(detalle));
    }

    @Test
    void testDelete() {
        doNothing().when(detalleFacturaRepository).deleteById(1L);
        detalleFacturaService.delete(1L);
        verify(detalleFacturaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdate() {
        DetalleFactura detalle = new DetalleFactura();
        detalle.setId(1L);
        when(detalleFacturaRepository.save(detalle)).thenReturn(detalle);

        assertEquals(1L, detalleFacturaService.update(detalle).getId());
    }
}
