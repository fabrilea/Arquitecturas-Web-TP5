package edu.tudai.microservicioadministrador.controller;

import edu.tudai.microservicioadministrador.dto.MonopatinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import  edu.tudai.microservicioadministrador.service.adminService;

import java.io.Reader;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class adminController {

    private final adminService adminService;

    @Operation(summary = "Anula una cuenta de usuario", description = "Desactiva una cuenta de usuario específica para que no pueda realizar operaciones.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta anulada correctamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @PutMapping("/anularCuenta/{id}")
    public ResponseEntity<Void> anularCuenta(
            @Parameter(description = "ID de la cuenta a anular", required = true)
            @PathVariable("id") long id) {
        adminService.anularCuenta(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtiene monopatines con más viajes", description = "Devuelve una lista de monopatines que han realizado más de un cierto número de viajes en un año dado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de monopatines obtenida con éxito"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @GetMapping("/monopatinViajes/{minViajes}/{anio}")
    public ResponseEntity<List<MonopatinDTO>> obtenerMonopatinesConMasViajes(
            @Parameter(description = "Número mínimo de viajes", required = true)
            @PathVariable int minViajes,
            @Parameter(description = "Año en el que se realizaron los viajes", required = true)
            @PathVariable int anio) {
        List<MonopatinDTO> result = adminService.obtenerMonopatinesConMasViajes(minViajes, anio);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Consulta el estado de los monopatines", description = "Devuelve un resumen de la cantidad de monopatines en operación y en mantenimiento.")
    @ApiResponse(responseCode = "200", description = "Estado de los monopatines devuelto con éxito")
    @GetMapping("/monopatines/comparacion")
    public ResponseEntity<Map<String, Long>> monopatinesVS() {
        Map<String, Long> result = adminService.getVSMonopatin();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Consulta el total facturado", description = "Devuelve el total facturado en un rango de meses de un año específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total facturado obtenido con éxito"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @GetMapping("/factura/total-facturado")
    public ResponseEntity<Double> obtenerTotalFacturado(
            @Parameter(description = "Año de la facturación", required = true)
            @RequestParam int anio,
            @Parameter(description = "Mes de inicio del rango", required = true)
            @RequestParam int mesInicio,
            @Parameter(description = "Mes de fin del rango", required = true)
            @RequestParam int mesFin) {
        double total = adminService.obtenerTotalFacturado(anio, mesInicio, mesFin);
        return ResponseEntity.ok(total);
    }

    @Operation(summary = "Ajusta los precios de las tarifas", description = "Permite al administrador ajustar la tarifa base y tarifa extra.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarifas ajustadas correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @PostMapping("/tarifa/ajustar-precios")
    public ResponseEntity<Void> ajustarPrecios(
            @Parameter(description = "Nueva tarifa base", required = true)
            @RequestParam double nuevaTarifaBase,
            @Parameter(description = "Nueva tarifa extra", required = true)
            @RequestParam double nuevaTarifaExtra,
            @Parameter(description = "Fecha de inicio de la nueva tarifa", required = true)
            @RequestParam LocalDate fechaInicio) {
        adminService.ajustarPrecios(nuevaTarifaBase, nuevaTarifaExtra, fechaInicio);
        return ResponseEntity.ok().build();
    }

}
