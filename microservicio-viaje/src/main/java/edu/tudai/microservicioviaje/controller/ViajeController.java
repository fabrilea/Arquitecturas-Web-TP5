package edu.tudai.microservicioviaje.controller;

import edu.tudai.microservicioviaje.dto.ReporteKilometrosDTO;
import edu.tudai.microservicioviaje.entity.Pausa;
import edu.tudai.microservicioviaje.entity.Viaje;
import edu.tudai.microservicioviaje.service.ViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/viaje")
public class ViajeController {

    private final ViajeService viajeService;

    @Operation(summary = "Obtiene todos los viajes", description = "Devuelve una lista de todos los viajes registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de viajes obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<Viaje>> getAllViajes(){
        List<Viaje> viajes = viajeService.findAll();
        if(viajes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(viajes);
    }

    @Operation(summary = "Obtiene un viaje por ID", description = "Devuelve los detalles del viaje correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viaje encontrado"),
            @ApiResponse(responseCode = "404", description = "Viaje no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Viaje> getViaje(@PathVariable("id") Long id){
        Viaje viaje = viajeService.findById(id);
        if(viaje == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(viaje);
    }

    @Operation(summary = "Crea un nuevo viaje", description = "Registra un nuevo viaje en el sistema.")
    @ApiResponse(responseCode = "201", description = "Viaje creado con éxito")
    @PostMapping
    public ResponseEntity<Viaje> createViaje(@RequestBody Viaje viaje){
        Viaje viajeCreated = viajeService.save(viaje);
        if(viajeCreated == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(viajeCreated);
    }

    @Operation(summary = "Elimina un viaje", description = "Elimina el viaje correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Viaje eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Viaje no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Viaje> deleteViaje(@PathVariable("id") Long id){
        viajeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualiza un viaje", description = "Actualiza los detalles de un viaje existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Viaje actualizado con éxito"),
            @ApiResponse(responseCode = "404", description = "Viaje no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Viaje> updateViaje(@PathVariable("id") Long id, @RequestBody Viaje viaje){
        Viaje viajeExistente = viajeService.findById(id);
        if(viajeExistente == null){
            return ResponseEntity.noContent().build();
        }

        viajeExistente.setFechaInicio(viaje.getFechaInicio());
        viajeExistente.setFechaFin(viaje.getFechaFin());
        viajeExistente.setMonopatinId(viaje.getMonopatinId());
        viajeExistente.setEnCurso(viaje.isEnCurso());
        viajeExistente.setKilometrosRecorridos(viaje.getKilometrosRecorridos());

        Viaje viajeUpdated = viajeService.save(viajeExistente);
        return ResponseEntity.ok(viajeUpdated);
    }


    /****************************************************/

    @GetMapping("/{viajeId}/tiempo-total-con-pausas")
    public ResponseEntity<Double> obtenerTiempoTotalConPausas(@PathVariable("viajeId") Long viajeId) {
        Double tiempoTotalConPausas = viajeService.calcularTiempoTotalConPausas(viajeId);
        return ResponseEntity.ok(tiempoTotalConPausas);
    }

    @PutMapping("/{viajeId}/finalizar")
    public ResponseEntity<Viaje> finalizarViaje(@PathVariable Long viajeId, @RequestParam double kilometrosRecorridos) {
        try {
            Viaje viajeFinalizado = viajeService.finalizarViaje(viajeId, kilometrosRecorridos);
            return ResponseEntity.ok(viajeFinalizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/agregarPausa/{id}")
    public ResponseEntity<Viaje> agregarPausa(@PathVariable("id") Long id, @RequestBody Pausa pausa) {
        Viaje viajeExistente = viajeService.findById(id);
        if (viajeExistente == null) {
            return ResponseEntity.noContent().build();
        }

        pausa.setViaje(viajeExistente);  // Asociar la pausa al viaje existente
        viajeExistente.getPausas().add(pausa);
        viajeService.save(viajeExistente);  // Guardar el viaje actualizado con la nueva pausa
        return ResponseEntity.ok(viajeExistente);
    }

    @GetMapping("/reportes/kilometros")
    public ResponseEntity<List<ReporteKilometrosDTO>> obtenerReporteKilometros() {
        List<ReporteKilometrosDTO> reporte = viajeService.generarReporteKilometros();
        if (reporte.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reporte);
    }
}
