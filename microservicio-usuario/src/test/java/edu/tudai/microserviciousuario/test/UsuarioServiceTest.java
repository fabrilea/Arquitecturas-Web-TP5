package edu.tudai.microserviciousuario.test;

import edu.tudai.microserviciousuario.service.UsuarioService;
import edu.tudai.microserviciousuario.repository.UsuarioRepository;
import edu.tudai.microserviciousuario.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuario() {
        Usuario usuario = new Usuario("juan@example.com", "Juan", "password123");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioService.crearUsuario(usuario);

        assertNotNull(result);
        assertEquals(usuario.getEmail(), result.getEmail());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testObtenerUsuarioPorId() {
        Long usuarioId = 1L;
        Usuario usuario = new Usuario("juan@example.com", "Juan", "password123");
        usuario.setId(usuarioId);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.obtenerUsuarioPorId(usuarioId);

        assertNotNull(result);
        assertEquals(usuarioId, result.getId());
        verify(usuarioRepository, times(1)).findById(usuarioId);
    }

    @Test
    void testActualizarUsuario() {
        Long usuarioId = 1L;
        Usuario usuarioExistente = new Usuario("juan@example.com", "Juan", "password123");
        usuarioExistente.setId(usuarioId);

        Usuario usuarioActualizado = new Usuario("juan@example.com", "Juan Carlos", "password123");

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioActualizado);

        Usuario result = usuarioService.actualizarUsuario(usuarioId, usuarioActualizado);

        assertEquals(usuarioActualizado.getNombre(), result.getNombre());
        verify(usuarioRepository, times(1)).save(usuarioExistente);
    }

    @Test
    void testEliminarUsuario() {
        Long usuarioId = 1L;

        doNothing().when(usuarioRepository).deleteById(usuarioId);

        usuarioService.eliminarUsuario(usuarioId);

        verify(usuarioRepository, times(1)).deleteById(usuarioId);
    }
}
