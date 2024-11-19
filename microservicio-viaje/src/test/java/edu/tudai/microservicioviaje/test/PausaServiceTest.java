package edu.tudai.microservicioviaje.test;

import edu.tudai.microservicioviaje.entity.Pausa;
import edu.tudai.microservicioviaje.repository.PausaRepository;
import edu.tudai.microservicioviaje.service.PausaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PausaServiceTest {

    @Mock
    private PausaRepository pausaRepository;

    @InjectMocks
    private PausaService pausaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(pausaRepository.findAll()).thenReturn(List.of(new Pausa()));
        assertFalse(pausaService.findAll().isEmpty());
    }

    @Test
    void testFindById() {
        Pausa pausa = new Pausa();
        pausa.setId(1L);
        when(pausaRepository.findById(1L)).thenReturn(Optional.of(pausa));
        assertNotNull(pausaService.findById(1L));
    }

    @Test
    void testSave() {
        Pausa pausa = new Pausa();
        when(pausaRepository.save(pausa)).thenReturn(pausa);
        assertNotNull(pausaService.save(pausa));
    }

    @Test
    void testDelete() {
        doNothing().when(pausaRepository).deleteById(1L);
        pausaService.delete(1L);
        verify(pausaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdate() {
        Pausa pausa = new Pausa();
        pausa.setId(1L);
        when(pausaRepository.save(pausa)).thenReturn(pausa);
        assertEquals(1L, pausaService.update(pausa).getId());
    }
}
