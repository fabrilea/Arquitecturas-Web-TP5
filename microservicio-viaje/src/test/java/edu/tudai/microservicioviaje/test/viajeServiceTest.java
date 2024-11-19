package edu.tudai.microservicioviaje.test;

import edu.tudai.microservicioviaje.entity.Viaje;
import edu.tudai.microservicioviaje.entity.Pausa;
import edu.tudai.microservicioviaje.repository.ViajeRepository;
import edu.tudai.microservicioviaje.service.ViajeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViajeServiceTest {

    @Mock
    private ViajeRepository viajeRepository;

    @InjectMocks
    private ViajeService viajeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(viajeRepository.findAll()).thenReturn(List.of(new Viaje()));
        assertFalse(viajeService.findAll().isEmpty());
    }

    @Test
    void testFindById() {
        Viaje viaje = new Viaje();
        viaje.setId(1L);
        when(viajeRepository.findById(1L)).thenReturn(Optional.of(viaje));
        assertNotNull(viajeService.findById(1L));
    }

    @Test
    void testSave() {
        Viaje viaje = new Viaje();
        when(viajeRepository.save(viaje)).thenReturn(viaje);
        assertNotNull(viajeService.save(viaje));
    }

    @Test
    void testDelete() {
        doNothing().when(viajeRepository).deleteById(1L);
        viajeService.delete(1L);
        verify(viajeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdate() {
        Viaje viaje = new Viaje();
        viaje.setId(1L);
        when(viajeRepository.save(viaje)).thenReturn(viaje);
        assertEquals(1L, viajeService.update(viaje).getId());
    }

    @Test
    void testAgregarPausa() {
        Viaje viaje = new Viaje();
        viaje.setId(1L);
        Pausa pausa = new Pausa();
        when(viajeRepository.findById(1L)).thenReturn(Optional.of(viaje));
        viajeService.agregarPausa(1L, pausa);
        verify(viajeRepository, times(1)).save(viaje);
    }

    @Test
    void testFinalizarViaje() {
        Viaje viaje = new Viaje();
        viaje.setId(1L);
        when(viajeRepository.findById(1L)).thenReturn(Optional.of(viaje));
        viajeService.finalizarViaje(1L);
        verify(viajeRepository, times(1)).save(viaje);
    }
}
