package edu.tudai.microserviciofactura.test;

import edu.tudai.microserviciofactura.entity.Tarifa;
import edu.tudai.microserviciofactura.repository.TarifaRepository;
import edu.tudai.microserviciofactura.service.TarifaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarifaServiceTest {

    @Mock
    private TarifaRepository tarifaRepository;

    @InjectMocks
    private TarifaService tarifaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(tarifaRepository.findAll()).thenReturn(List.of(new Tarifa()));
        assertFalse(tarifaService.findAll().isEmpty());
    }

    @Test
    void testFindById() {
        Tarifa tarifa = new Tarifa();
        tarifa.setId(1L);
        when(tarifaRepository.findById(1L)).thenReturn(Optional.of(tarifa));

        assertNotNull(tarifaService.findById(1L));
    }

    @Test
    void testSave() {
        Tarifa tarifa = new Tarifa();
        when(tarifaRepository.save(tarifa)).thenReturn(tarifa);

        assertNotNull(tarifaService.save(tarifa));
    }

    @Test
    void testDelete() {
        doNothing().when(tarifaRepository).deleteById(1L);
        tarifaService.delete(1L);
        verify(tarifaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAjustarPrecios() {
        tarifaService.ajustarPrecios(10.0, 5.0);
        verify(tarifaRepository, times(1)).findAll();
    }
}
