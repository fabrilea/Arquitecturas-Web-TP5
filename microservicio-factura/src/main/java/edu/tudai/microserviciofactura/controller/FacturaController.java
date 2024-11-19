package edu.tudai.microserviciofactura.controller;

import edu.tudai.microserviciofactura.entity.Factura;
import edu.tudai.microserviciofactura.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/factura")
public class FacturaController {

    private final FacturaService facturaService;


    @Operation(summary = "Obtiene todas las facturas", description = "Devuelve una lista de todas las facturas registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<Factura>> getAllFacturas() {
        List<Factura> facturas = facturaService.findAll();
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }

    @Operation(summary = "Obtiene una factura por ID", description = "Devuelve los detalles de la factura correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura encontrada"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Factura> getFacturaById(@PathVariable("id") Long id) {
        Factura factura = facturaService.findById(id);
        if (factura == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(factura);
    }

    @Operation(summary = "Crea una nueva factura", description = "Registra una nueva factura en el sistema.")
    @ApiResponse(responseCode = "201", description = "Factura creada con éxito")
    @PostMapping
    public ResponseEntity<Factura> createFactura(@RequestBody Factura factura) {
        Factura facturaCreated = facturaService.save(factura);
        if (facturaCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facturaCreated);
    }

    @Operation(summary = "Elimina una factura", description = "Elimina la factura correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Factura eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactura(@PathVariable("id") Long id) {
        facturaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Factura> updateFactura(@PathVariable("id") Long id, @RequestBody Factura factura) {
        Factura facturaExistente = facturaService.findById(id);

        if (facturaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        facturaExistente.setUsuarioId(factura.getUsuarioId());
        facturaExistente.setFechaEmision(factura.getFechaEmision());

        Factura facturaUpdated = facturaService.save(facturaExistente);

        return ResponseEntity.ok(facturaUpdated);
    }

    /*******************************************/

    @GetMapping("/total-facturado")
    public ResponseEntity<Double> obtenerTotalFacturado(
            @RequestParam int anio, @RequestParam int mesInicio, @RequestParam int mesFin) {
        double total = facturaService.obtenerTotalFacturado(anio, mesInicio, mesFin);
        return ResponseEntity.ok(total);
    }
}
