package edu.tudai.microserviciofactura.controller;

import edu.tudai.microserviciofactura.entity.Tarifa;
import edu.tudai.microserviciofactura.service.TarifaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/tarifa")
public class TarifaController {

    private final TarifaService tarifaService;

    @Operation(summary = "Obtiene todas las tarifas", description = "Devuelve una lista de todas las tarifas registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de tarifas obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<Tarifa>> getAllTarifas() {
        List<Tarifa> tarifas = tarifaService.findAll();
        if (tarifas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tarifas);
    }

    @Operation(summary = "Obtiene una factura por ID", description = "Devuelve los detalles del detalle de factura correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "factura encontrado"),
            @ApiResponse(responseCode = "404", description = "factura no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> getFacturaById(@PathVariable("id") Long id) {
        Tarifa tarifa = tarifaService.findById(id);
        if (tarifa == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tarifa);
    }

    @Operation(summary = "Crea una nueva tarifa", description = "Registra una nueva tarifa en el sistema.")
    @ApiResponse(responseCode = "201", description = "Tarifa creada con éxito")
    @PostMapping
    public ResponseEntity<Tarifa> createTarifa(@RequestBody Tarifa tarifa) {
        Tarifa tarifaCreated = tarifaService.save(tarifa);
        if (tarifaCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tarifaCreated);
    }

    @Operation(summary = "Elimina una tarifa", description = "Elimina la parada correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "tarifa eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "tarifa no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarifa(@PathVariable("id") Long id) {
        tarifaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualiza una tarifa", description = "Actualiza los detalles de una parada existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "tarifa actualizada con éxito"),
            @ApiResponse(responseCode = "404", description = "tarifa no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Tarifa> updateTarifa(@PathVariable("id") Long id, @RequestBody Tarifa tarifa) {
        Tarifa tarifaExistente = tarifaService.findById(id);

        if (tarifaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        tarifaExistente.setTipo(tarifa.getTipo());
        tarifaExistente.setFechaInicio(tarifa.getFechaInicio());
        tarifaExistente.setFechaFin(tarifa.getFechaFin());
        tarifaExistente.setMonto(tarifa.getMonto());

        Tarifa tarifaUpdated = tarifaService.save(tarifaExistente);

        return ResponseEntity.ok(tarifaUpdated);
    }

    /****************************************/

    @Operation(summary = "Ajusta precios de tarifas", description = "Ajusta los precios de todas las tarifas.")
    @ApiResponse(responseCode = "200", description = "Precios ajustados con éxito")
    @PostMapping("/ajustar-precios")
    public ResponseEntity<Void> ajustarPrecios(
            @RequestParam double nuevaTarifaBase,
            @RequestParam double nuevaTarifaExtra,
            @RequestParam LocalDate fechaInicio) {
        tarifaService.ajustarPrecios(nuevaTarifaBase, nuevaTarifaExtra, fechaInicio);
        return ResponseEntity.ok().build();
    }
}
