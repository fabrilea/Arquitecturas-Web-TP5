package edu.tudai.microserviciousuario.controller;

import edu.tudai.microserviciousuario.dto.MonopatinDTO;
import edu.tudai.microserviciousuario.entity.Usuario;
import edu.tudai.microserviciousuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Obtiene todos los usuarios", description = "Devuelve una lista de todos los usuarios.")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida con éxito")
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Obtiene un usuario por ID", description = "Devuelve los detalles del usuario correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(
            @Parameter(description = "ID del usuario", required = true) @PathVariable("id") Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Crea un nuevo usuario", description = "Registra un nuevo usuario en el sistema.")
    @ApiResponse(responseCode = "201", description = "Usuario creado con éxito")
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(
            @Parameter(description = "Información del usuario") @RequestBody Usuario usuario) {
        Usuario usuarioCreated = usuarioService.save(usuario);
        if (usuarioCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreated);
    }

    @Operation(summary = "Elimina un usuario", description = "Elimina el usuario correspondiente al ID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("id") Long id) {
        usuarioService.delete(id);
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Actualiza un usuario", description = "Actualiza los detalles de un usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado con éxito"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        Usuario usuarioExistente = usuarioService.findById(id);

        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build();
        }

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setCelular(usuario.getCelular());
        usuarioExistente.setEmail(usuario.getEmail());

        Usuario usuarioUpdated = usuarioService.update(usuarioExistente);

        return ResponseEntity.ok(usuarioUpdated);
    }

    /*******************************************************************/

    @GetMapping("/monopatin/cercanos")
    public ResponseEntity<List<MonopatinDTO>> obtenerMonopatinesCercanos(@RequestParam double latitud, @RequestParam double longitud, @RequestParam double radio){
        List<MonopatinDTO> monopatines = usuarioService.obtenerMonopatinesCercanos(latitud, longitud, radio);
        if (monopatines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(monopatines);
    }

}
