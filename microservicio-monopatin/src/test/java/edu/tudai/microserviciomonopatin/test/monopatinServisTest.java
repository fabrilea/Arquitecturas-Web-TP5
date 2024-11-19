package edu.tudai.microserviciomonopatin.test;

import edu.tudai.microserviciomonopatin.entity.Monopatin;
import edu.tudai.microserviciomonopatin.repository.MonopatinRepository;
import edu.tudai.microserviciomonopatin.service.MonopatinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonopatinServiceTest {

    @Mock
    private MonopatinRepository monopatinRepository;

    @InjectMocks
    private MonopatinService monopatinService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(monopatinRepository.findAll()).thenReturn(List.of(new Monopatin()));
        List<Monopatin> result = monopatinService.findAll();
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindById() {
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);
        when(monopatinRepository.findById(1L)).thenReturn(Optional.of(monopatin));
        Monopatin result = monopatinService.findById(1L);
        assertNotNull(result);
    }

    @Test
    void testSave() {
        Monopatin monopatin = new Monopatin();
        when(monopatinRepository.save(monopatin)).thenReturn(monopatin);
        Monopatin result = monopatinService.save(monopatin);
        assertNotNull(result);
    }

    @Test
    void testDelete() {
        doNothing().when(monopatinRepository).deleteById(1L);
        monopatinService.delete(1L);
        verify(monopatinRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdate() {
        Monopatin monopatin = new Monopatin();
        monopatin.setId(1L);
        when(monopatinRepository.save(monopatin)).thenReturn(monopatin);
        Monopatin result = monopatinService.update(monopatin);
        assertEquals(1L, result.getId());
    }

    @Test
    void testObtenerEstadoMonopatines() {
        when(monopatinRepository.countByEstado("DISPONIBLE")).thenReturn(5L);
        long disponibles = monopatinService.obtenerEstadoMonopatines().get("DISPONIBLE");
        assertEquals(5L, disponibles);
    }

    @Test
    void testObtenerMonopatinesCercanos() {
        when(monopatinRepository.findMonopatinesCercanos(40.0, -74.0, 10.0))
                .thenReturn(List.of(new Monopatin()));
        List<Monopatin> result = monopatinService.obtenerMonopatinesCercanos(40.0, -74.0, 10.0);
        assertFalse(result.isEmpty());
    }
}