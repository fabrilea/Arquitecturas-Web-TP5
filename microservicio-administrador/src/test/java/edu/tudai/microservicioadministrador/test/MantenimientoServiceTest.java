package edu.tudai.microservicioadministrador.test;

import edu.tudai.microserviciomantenimiento.client.MonopatinClient;
import edu.tudai.microserviciomantenimiento.entity.Mantenimiento;
import edu.tudai.microserviciomantenimiento.repository.MantenimientoRepository;
import edu.tudai.microserviciomantenimiento.service.MantenimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MantenimientoServiceTest {

    @InjectMocks
    private MantenimientoService mantenimientoService;

    @Mock
    private MantenimientoRepository mantenimientoRepository;

    @Mock
    private MonopatinClient monopatinClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIniciarMantenimiento() {
        Long monopatinId = 1L;
        String descripcion = "Revisión de frenos";

        // Simulación de Monopatin en mantenimiento
        when(monopatinClient.marcarEnMantenimiento(monopatinId)).thenReturn(true);

        // Simulación de almacenamiento en repositorio
        Mantenimiento mantenimiento = new Mantenimiento(monopatinId, descripcion, LocalDate.now(), null);
        when(mantenimientoRepository.save(any(Mantenimiento.class))).thenReturn(mantenimiento);

        Mantenimiento result = mantenimientoService.iniciarMantenimiento(monopatinId, descripcion);

        assertNotNull(result);
        assertEquals(monopatinId, result.getMonopatinId());
        assertEquals(descripcion, result.getDescripcion());
        assertNull(result.getFechaFin());
        verify(mantenimientoRepository, times(1)).save(any(Mantenimiento.class));
    }

    @Test
    void testFinalizarMantenimiento() {
        Long monopatinId = 1L;

        Mantenimiento mantenimiento = new Mantenimiento(monopatinId, "Revisión", LocalDate.now(), null);
        when(mantenimientoRepository.findByMonopatinIdAndFechaFinIsNull(monopatinId)).thenReturn(Optional.of(mantenimiento));
        when(monopatinClient.marcarDisponible(monopatinId)).thenReturn(true);

        Mantenimiento result = mantenimientoService.finalizarMantenimiento(monopatinId);

        assertNotNull(result.getFechaFin());
        verify(mantenimientoRepository, times(1)).save(mantenimiento);
    }

    @Test
    void testConsultarEstadoMantenimiento() {
        Long monopatinId = 1L;
        Mantenimiento mantenimiento = new Mantenimiento(monopatinId, "Revisión", LocalDate.now(), null);
        when(mantenimientoRepository.findByMonopatinIdAndFechaFinIsNull(monopatinId)).thenReturn(Optional.of(mantenimiento));

        String estado = mantenimientoService.consultarEstadoMantenimiento(monopatinId);

        assertEquals("En mantenimiento", estado);
    }

    @Test
    void testActualizarMantenimiento() {
        Long mantenimientoId = 1L;
        Mantenimiento mantenimientoExistente = new Mantenimiento(1L, "Revisión", LocalDate.now(), null);
        when(mantenimientoRepository.findById(mantenimientoId)).thenReturn(Optional.of(mantenimientoExistente));

        Mantenimiento mantenimientoActualizado = new Mantenimiento(1L, "Revisión completa", LocalDate.now(), LocalDate.now().plusDays(1));

        Mantenimiento result = mantenimientoService.actualizarMantenimiento(mantenimientoId, mantenimientoActualizado);

        assertEquals(mantenimientoActualizado.getDescripcion(), result.getDescripcion());
        assertEquals(mantenimientoActualizado.getFechaInicio(), result.getFechaInicio());
        verify(mantenimientoRepository, times(1)).save(mantenimientoExistente);
    }
}