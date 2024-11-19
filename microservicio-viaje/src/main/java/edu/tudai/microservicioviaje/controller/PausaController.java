package edu.tudai.microservicioviaje.controller;

import edu.tudai.microservicioviaje.entity.Pausa;
import edu.tudai.microservicioviaje.service.PausaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pausa")
public class PausaController {

    private final PausaService pausaService;

    @Operation(summary = "Obtiene todas las pausas", description = "Devuelve una lista de todas las pausas registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de pausas obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<Pausa>> getAllPausas() {
        List<Pausa> pausas = pausaService.findAll();
        if (pausas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pausas);
    }

    @Operation(summary = "Obtiene una pausa por ID", description = "Devuelve los detalles de la pausa correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pausa encontrada"),
            @ApiResponse(responseCode = "404", description = "Pausa no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pausa> getPausaById(@PathVariable Long id) {
        Pausa pausa = pausaService.findById(id);
        return pausa != null ? ResponseEntity.ok(pausa) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crea una nueva pausa", description = "Registra una nueva pausa durante un viaje.")
    @ApiResponse(responseCode = "201", description = "Pausa creada con éxito")
    @PostMapping
    public ResponseEntity<Pausa> createPausa(@RequestBody Pausa pausa) {
        Pausa createdPausa = pausaService.save(pausa);
        return ResponseEntity.ok(createdPausa);
    }

    @Operation(summary = "Actualiza una pausa", description = "Actualiza los detalles de una pausa existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pausa actualizada con éxito"),
            @ApiResponse(responseCode = "404", description = "Pausa no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pausa> updatePausa(@PathVariable Long id, @RequestBody Pausa pausa) {
        Pausa updatedPausa = pausaService.update(id, pausa);
        return updatedPausa != null ? ResponseEntity.ok(updatedPausa) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Elimina una pausa", description = "Elimina la pausa correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pausa eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Pausa no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePausa(@PathVariable Long id) {
        pausaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
