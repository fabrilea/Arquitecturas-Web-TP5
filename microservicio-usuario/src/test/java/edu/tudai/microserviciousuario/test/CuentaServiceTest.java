package edu.tudai.microserviciousuario.test;

import edu.tudai.microserviciousuario.service.CuentaService;
import edu.tudai.microserviciousuario.repository.CuentaRepository;
import edu.tudai.microserviciousuario.entity.Cuenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CuentaServiceTest {

    @InjectMocks
    private CuentaService cuentaService;

    @Mock
    private CuentaRepository cuentaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearCuenta() {
        Cuenta cuenta = new Cuenta("juan@example.com", "password123");

        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        Cuenta result = cuentaService.crearCuenta(cuenta);

        assertNotNull(result);
        assertEquals(cuenta.getEmail(), result.getEmail());
        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void testObtenerCuentaPorId() {
        Long cuentaId = 1L;
        Cuenta cuenta = new Cuenta("juan@example.com", "password123");
        cuenta.setId(cuentaId);

        when(cuentaRepository.findById(cuentaId)).thenReturn(Optional.of(cuenta));

        Cuenta result = cuentaService.obtenerCuentaPorId(cuentaId);

        assertNotNull(result);
        assertEquals(cuentaId, result.getId());
        verify(cuentaRepository, times(1)).findById(cuentaId);
    }

    @Test
    void testActualizarCuenta() {
        Long cuentaId = 1L;
        Cuenta cuentaExistente = new Cuenta("juan@example.com", "password123");
        cuentaExistente.setId(cuentaId);

        Cuenta cuentaActualizada = new Cuenta("juan@example.com", "newpassword123");

        when(cuentaRepository.findById(cuentaId)).thenReturn(Optional.of(cuentaExistente));
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuentaActualizada);

        Cuenta result = cuentaService.actualizarCuenta(cuentaId, cuentaActualizada);

        assertEquals(cuentaActualizada.getPassword(), result.getPassword());
        verify(cuentaRepository, times(1)).save(cuentaExistente);
    }

    @Test
    void testAnularCuenta() {
        Long cuentaId = 1L;

        doNothing().when(cuentaRepository).deleteById(cuentaId);

        cuentaService.anularCuenta(cuentaId);

        verify(cuentaRepository, times(1)).deleteById(cuentaId);
    }
}
