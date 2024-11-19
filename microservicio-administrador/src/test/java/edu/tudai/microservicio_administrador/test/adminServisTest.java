package edu.tudai.microservicio_administrador.test;

import edu.tudai.microservicioadministrador.client.CuentaClient;
import edu.tudai.microservicioadministrador.client.FacturaClient;
import edu.tudai.microservicioadministrador.client.MonopatinClient;
import edu.tudai.microservicioadministrador.client.TarifaClient;
import edu.tudai.microservicioadministrador.dto.MonopatinDTO;
import edu.tudai.microservicioadministrador.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private MonopatinClient monopatinClient;

    @Mock
    private CuentaClient cuentaClient;

    @Mock
    private FacturaClient facturaClient;

    @Mock
    private TarifaClient tarifaClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAnularCuenta() {
        long cuentaId = 1L;

        doNothing().when(cuentaClient).anularCuenta(cuentaId);

        adminService.anularCuenta(cuentaId);

        verify(cuentaClient, times(1)).anularCuenta(cuentaId);
    }

    @Test
    void testObtenerMonopatinesConMasViajes() {
        int minViajes = 5;
        int anio = 2023;

        List<MonopatinDTO> mockResponse = List.of(new MonopatinDTO());
        when(monopatinClient.obtenerMonopatinesConMasViajes(minViajes, anio)).thenReturn(mockResponse);

        List<MonopatinDTO> result = adminService.obtenerMonopatinesConMasViajes(minViajes, anio);

        assertEquals(mockResponse, result);
        verify(monopatinClient, times(1)).obtenerMonopatinesConMasViajes(minViajes, anio);
    }

    @Test
    void testGetVSMonopatin() {
        Map<String, Long> mockResponse = Map.of("operativos", 10L, "enMantenimiento", 5L);
        when(monopatinClient.obtenerEstadoMonopatines()).thenReturn(mockResponse);

        Map<String, Long> result = adminService.getVSMonopatin();

        assertEquals(mockResponse, result);
        verify(monopatinClient, times(1)).obtenerEstadoMonopatines();
    }

    @Test
    void testObtenerTotalFacturado() {
        int anio = 2023;
        int mesInicio = 1;
        int mesFin = 3;
        double mockTotal = 5000.0;

        when(facturaClient.obtenerTotalFacturado(anio, mesInicio, mesFin)).thenReturn(mockTotal);

        double result = adminService.obtenerTotalFacturado(anio, mesInicio, mesFin);

        assertEquals(mockTotal, result);
        verify(facturaClient, times(1)).obtenerTotalFacturado(anio, mesInicio, mesFin);
    }

    @Test
    void testAjustarPrecios() {
        double nuevaTarifaBase = 10.0;
        double nuevaTarifaExtra = 2.0;
        LocalDate fechaInicio = LocalDate.now();

        doNothing().when(tarifaClient).ajustarPrecios(nuevaTarifaBase, nuevaTarifaExtra, fechaInicio);

        adminService.ajustarPrecios(nuevaTarifaBase, nuevaTarifaExtra, fechaInicio);

        verify(tarifaClient, times(1)).ajustarPrecios(nuevaTarifaBase, nuevaTarifaExtra, fechaInicio);
    }
}