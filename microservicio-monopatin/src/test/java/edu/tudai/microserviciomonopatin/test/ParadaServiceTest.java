package edu.tudai.microserviciomonopatin.test;

import edu.tudai.microserviciomonopatin.entity.Parada;
import edu.tudai.microserviciomonopatin.repository.ParadaRepository;
import edu.tudai.microserviciomonopatin.service.ParadaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParadaServiceTest {

    @Mock
    private ParadaRepository paradaRepository;

    @InjectMocks
    private ParadaService paradaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(paradaRepository.findAll()).thenReturn(List.of(new Parada()));
        assertFalse(paradaService.findAll().isEmpty());
    }

    @Test
    void testFindById() {
        Parada parada = new Parada();
        parada.setId(1L);
        when(paradaRepository.findById(1L)).thenReturn(Optional.of(parada));
        assertNotNull(paradaService.findById(1L));
    }

    @Test
    void testSave() {
        Parada parada = new Parada();
        when(paradaRepository.save(parada)).thenReturn(parada);
        assertNotNull(paradaService.save(parada));
    }

    @Test
    void testDelete() {
        doNothing().when(paradaRepository).deleteById(1L);
        paradaService.delete(1L);
        verify(paradaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdate() {
        Parada parada = new Parada();
        parada.setId(1L);
        when(paradaRepository.save(parada)).thenReturn(parada);
        assertEquals(1L, paradaService.update(parada).getId());
    }
}
