package edu.tudai.microserviciofactura.controller;

import edu.tudai.microserviciofactura.dto.DetalleFacturaDTO;
import edu.tudai.microserviciofactura.entity.DetalleFactura;
import edu.tudai.microserviciofactura.entity.Factura;
import edu.tudai.microserviciofactura.repository.FacturaRepository;
import edu.tudai.microserviciofactura.service.DetalleFacturaService;
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
@RequestMapping("/api/detallefactura")
public class DetalleFacturaController {

    private final DetalleFacturaService detalleFacturaService;
    private final FacturaService facturaService;


    @Operation(summary = "Obtiene todos los detalles de factura", description = "Devuelve una lista de todos los detalles de factura registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de detalles de factura obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<DetalleFactura>> getAllDetallesFactura() {
        List<DetalleFactura> detalleFacturas = detalleFacturaService.findAll();
        if (detalleFacturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detalleFacturas);
    }

    @Operation(summary = "Obtiene un detalle de factura por ID", description = "Devuelve los detalles del detalle de factura correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle de factura encontrado"),
            @ApiResponse(responseCode = "404", description = "Detalle de factura no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DetalleFactura> getDetalleFacturaById(@PathVariable("id") Long id) {
        DetalleFactura detalleFactura = detalleFacturaService.findById(id);
        if (detalleFactura == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(detalleFactura);
    }

    @Operation(summary = "Crea un nuevo detalle de factura", description = "Registra un nuevo detalle de factura.")
    @ApiResponse(responseCode = "201", description = "Detalle de factura creado con éxito")
    @PostMapping
    public ResponseEntity<DetalleFactura> createDetalleFactura(@RequestBody DetalleFacturaDTO detalleDTO) {
        Factura factura = facturaService.findById(detalleDTO.getFacturaId());

        DetalleFactura detalle = new DetalleFactura(factura, detalleDTO.getViajeId(),
                detalleDTO.getTarifaBase(), detalleDTO.getTarifaExtra(),
                detalleDTO.getTiempoUso(), detalleDTO.getTiempoPausado());

        detalle.calcularMonto();  // Calcular el monto antes de guardar
        detalleFacturaService.save(detalle);
        return ResponseEntity.ok(detalle);
    }

    @Operation(summary = "Elimina un detalle de factura", description = "Elimina la parada correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "detalle eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "factura no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalleFactura(@PathVariable("id") Long id) {
        detalleFacturaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "detalle de factura una parada", description = "Actualiza los detalles de una parada existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "detalle actualizada con éxito"),
            @ApiResponse(responseCode = "404", description = "factura no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DetalleFactura> updateDetalleFactura(@PathVariable("id") Long id, @RequestBody DetalleFactura detalleFactura) {
        DetalleFactura detalleFacturaExistente = detalleFacturaService.findById(id);

        if (detalleFacturaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        detalleFacturaExistente.setFactura(detalleFactura.getFactura());
        detalleFacturaExistente.setTiempoUso(detalleFactura.getTiempoUso());
        detalleFacturaExistente.setTiempoPausado(detalleFactura.getTiempoPausado());
        detalleFacturaExistente.setTarifaBase(detalleFactura.getTarifaBase());
        detalleFacturaExistente.setTarifaExtra(detalleFactura.getTarifaExtra());
        detalleFacturaExistente.setViajeId(detalleFactura.getViajeId());

        DetalleFactura detalleFacturaUpdated = detalleFacturaService.update(detalleFacturaExistente);

        return ResponseEntity.ok(detalleFacturaUpdated);
    }
}
