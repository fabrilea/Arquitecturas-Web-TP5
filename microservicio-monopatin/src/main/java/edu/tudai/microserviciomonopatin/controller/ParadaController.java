package edu.tudai.microserviciomonopatin.controller;

import edu.tudai.microserviciomonopatin.entity.Parada;
import edu.tudai.microserviciomonopatin.service.ParadaService;
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
@RequestMapping("/api/parada")
public class ParadaController {

    private final ParadaService paradaService;

    @Operation(summary = "Obtiene todas las paradas", description = "Devuelve una lista de todas las paradas registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de paradas obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<Parada>> getAllParadas(){
        List<Parada> paradas = paradaService.findAll();
        if(paradas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paradas);
    }

    @Operation(summary = "Obtiene una parada por ID", description = "Devuelve los detalles de la parada correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parada encontrada"),
            @ApiResponse(responseCode = "404", description = "Parada no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Parada> getParadaById(@PathVariable("id") Long id){
        Parada parada = paradaService.findById(id);
        if(parada == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(parada);
    }

    @Operation(summary = "Crea una nueva parada", description = "Registra una nueva parada en el sistema.")
    @ApiResponse(responseCode = "201", description = "Parada creada con éxito")
    @PostMapping
    public ResponseEntity<Parada> createParada(@RequestBody Parada parada){
        Parada paradaCreated = paradaService.save(parada);
        if(paradaCreated == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paradaCreated);
    }

    @Operation(summary = "Elimina una parada", description = "Elimina la parada correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Parada eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Parada no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Parada> deleteParada(@PathVariable("id") Long id){
        paradaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualiza una parada", description = "Actualiza los detalles de una parada existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parada actualizada con éxito"),
            @ApiResponse(responseCode = "404", description = "Parada no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Parada> updateParada(@PathVariable("id") Long id, @RequestBody Parada parada){
        Parada paradaExistente = paradaService.findById(id);
        if(paradaExistente == null){
            return ResponseEntity.noContent().build();
        }

        //paradaExistente.setDireccion(parada.getDireccion());
        paradaExistente.setNombre(parada.getNombre());
        paradaExistente.setLatitud(parada.getLatitud());
        paradaExistente.setLongitud(parada.getLongitud());

        Parada paradaUpdated = paradaService.update(paradaExistente);

        return ResponseEntity.ok(paradaUpdated);
    }
}
