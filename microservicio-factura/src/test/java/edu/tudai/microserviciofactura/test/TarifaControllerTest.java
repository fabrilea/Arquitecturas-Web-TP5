package edu.tudai.microserviciofactura.test;

import edu.tudai.microserviciofactura.controller.TarifaController;
import edu.tudai.microserviciofactura.entity.Tarifa;
import edu.tudai.microserviciofactura.service.TarifaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarifaControllerTest {

    @Mock
    private TarifaService tarifaService;

    @InjectMocks
    private TarifaController tarifaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTarifas() {
        when(tarifaService.findAll()).thenReturn(List.of(new Tarifa()));
        ResponseEntity<List<Tarifa>> response = tarifaController.getAllTarifas();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCreateTarifa() {
        Tarifa tarifa = new Tarifa();
        when(tarifaService.save(tarifa)).thenReturn(tarifa);

        ResponseEntity<Tarifa> response = tarifaController.createTarifa(tarifa);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testAjustarPrecios() {
        doNothing().when(tarifaService).ajustarPrecios(10.0, 5.0);
        ResponseEntity<Void> response = tarifaController.ajustarPrecios(10.0, 5.0);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteTarifa() {
        doNothing().when(tarifaService).delete(1L);
        ResponseEntity<Void> response = tarifaController.deleteTarifa(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
