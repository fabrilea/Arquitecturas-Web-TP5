package edu.tudai.microserviciousuario.controller;

import edu.tudai.microserviciousuario.entity.Cuenta;
import edu.tudai.microserviciousuario.entity.Usuario;
import edu.tudai.microserviciousuario.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/cuenta")
public class CuentaController {

    private final CuentaService cuentaService;

    @Operation(summary = "Obtiene todas las cuentas", description = "Devuelve una lista de todas las cuentas.")
    @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<Cuenta>> getAllCuentas() {
        List<Cuenta> cuentas = cuentaService.findAll();
        if (cuentas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuentas);
    }

    @Operation(summary = "Obtiene una cuenta por ID", description = "Devuelve los detalles de la cuenta correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable("id") Long id) {
        Cuenta cuenta = cuentaService.findById(id);
        if (cuenta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cuenta);
    }

    @Operation(summary = "Crea una nueva cuenta", description = "Registra una nueva cuenta en el sistema.")
    @ApiResponse(responseCode = "201", description = "Cuenta creada con éxito")
    @PostMapping
    public ResponseEntity<Cuenta> createCuenta(@RequestBody Cuenta cuenta) {
        Cuenta cuentaCreated = cuentaService.save(cuenta);
        if (cuentaCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cuentaCreated);
    }

    @Operation(summary = "Elimina una cuenta", description = "Elimina la cuenta correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuenta eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("id") Long id) {
        cuentaService.delete(id);
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Actualiza una cuenta", description = "Actualiza los detalles de una cuenta existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta actualizada con éxito"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable("id") Long id, @RequestBody Cuenta cuenta) {
        Cuenta cuentaExistente = cuentaService.findById(id);

        if (cuentaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        cuentaExistente.setFechaAlta(cuenta.getFechaAlta());
        cuentaExistente.setSaldo(cuenta.getSaldo());

        Cuenta cuentaUpdated = cuentaService.update(cuentaExistente);

        return ResponseEntity.ok(cuentaUpdated);
    }

    @Operation(summary = "Anula una cuenta", description = "Desactiva una cuenta específica para que no pueda realizar operaciones.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta anulada correctamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @PutMapping("/anular/{id}")
    public ResponseEntity<Cuenta>  anularCuenta(@PathVariable("id") long id) {

        Cuenta cuenta = cuentaService.findById(id);

        cuentaService.anularCuenta(cuenta);

        return ResponseEntity.ok(cuenta);
    }

    @Operation(summary = "activa una cuenta", description = "Actualiza los detalles de una cuenta existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta activada con éxito"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @PutMapping("/activarUsuario/{id}")
    public ResponseEntity<Cuenta> activarUsuarioEnCuenta(@PathVariable("id") Long id, @RequestParam Long usuarioId) {
        Cuenta cuenta = cuentaService.activarUsuarioEnCuenta(id, usuarioId);
        if (cuenta == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cuenta);
    }

}
