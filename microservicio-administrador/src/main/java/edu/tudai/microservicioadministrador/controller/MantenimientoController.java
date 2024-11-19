package edu.tudai.microservicioadministrador.controller;

import edu.tudai.microservicioadministrador.dto.ReporteKilometrosDTO;
import edu.tudai.microservicioadministrador.entity.Mantenimiento;
import edu.tudai.microservicioadministrador.service.MantenimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/mantenimiento")
public class MantenimientoController {

    private final MantenimientoService mantenimientoService;

    @GetMapping
    public ResponseEntity<List<Mantenimiento>> getAllMantenimientos() {
        List<Mantenimiento> mantenimientos = mantenimientoService.findAll();
        if (mantenimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mantenimientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mantenimiento> getMantenimientoById(@PathVariable("id") Long id) {
        Mantenimiento mantenimiento = mantenimientoService.findById(id);
        if (mantenimiento == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mantenimiento);
    }

    @PostMapping
    public ResponseEntity<Mantenimiento> createMantenimiento(@RequestBody Mantenimiento mantenimiento) {
        Mantenimiento mantenimientoCreated = mantenimientoService.save(mantenimiento);
        if (mantenimientoCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mantenimientoCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMantenimiento(@PathVariable("id") Long id) {
        mantenimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mantenimiento> updatemantenimiento(@PathVariable("id") Long id, @RequestBody Mantenimiento mantenimiento) {
        Mantenimiento mantenimientoExistente = mantenimientoService.findById(id);

        if (mantenimientoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        mantenimientoExistente.setDescripcion(mantenimiento.getDescripcion());
        mantenimientoExistente.setMonopatinId(mantenimiento.getMonopatinId());
        mantenimientoExistente.setFechaInicio(mantenimiento.getFechaInicio());
        mantenimientoExistente.setFechaFin(mantenimiento.getFechaFin());

        Mantenimiento mantenimientoUpdated = mantenimientoService.update(mantenimientoExistente);

        return ResponseEntity.ok(mantenimientoUpdated);
    }

    /*****************************************************/

    @Operation(summary = "Inicia mantenimiento de un monopatín", description = "Marca un monopatín como en mantenimiento, estableciendo una descripción y fecha de inicio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mantenimiento iniciado correctamente"),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
    })
    @PostMapping("/iniciar/{monopatinId}")
    public ResponseEntity<Mantenimiento> iniciarMantenimiento(
            @Parameter(description = "ID del monopatín a poner en mantenimiento", required = true)
            @PathVariable Long monopatinId,
            @Parameter(description = "Descripción del mantenimiento")
            @RequestBody(required = false) String descripcion) {
        Mantenimiento mantenimiento = mantenimientoService.iniciarMantenimiento(monopatinId, descripcion);
        return ResponseEntity.ok(mantenimiento);
    }

    @Operation(summary = "Finaliza mantenimiento de un monopatín", description = "Marca el fin de mantenimiento de un monopatín, haciéndolo nuevamente disponible.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mantenimiento finalizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Mantenimiento o monopatín no encontrado")
    })
    @PutMapping("/finalizar/{monopatinId}")
    public ResponseEntity<Mantenimiento> finalizarMantenimiento(
            @Parameter(description = "ID del monopatín a finalizar el mantenimiento", required = true)
            @PathVariable Long monopatinId) {
        Mantenimiento mantenimiento = mantenimientoService.finalizarMantenimiento(monopatinId);
        return ResponseEntity.ok(mantenimiento);
    }

    @Operation(summary = "Genera un reporte de uso de monopatines", description = "Genera un reporte de kilómetros recorridos por los monopatines.")
    @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente")
    @GetMapping("/reporte-uso")
    public ResponseEntity<List<ReporteKilometrosDTO>> generarReporteUsoMonopatines(
            @Parameter(description = "Incluir pausas en el reporte")
            @RequestParam boolean incluirPausas) {
        return ResponseEntity.ok(mantenimientoService.generarReporteUsoMonopatines(incluirPausas));
    }
}
