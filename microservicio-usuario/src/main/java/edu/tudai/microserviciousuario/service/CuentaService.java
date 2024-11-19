package edu.tudai.microserviciousuario.service;

import edu.tudai.microserviciousuario.entity.Cuenta;
import edu.tudai.microserviciousuario.entity.Usuario;
import edu.tudai.microserviciousuario.repository.CuentaRepository;
import edu.tudai.microserviciousuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    @Transactional
    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public Cuenta update(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public void delete(Long id) {
        cuentaRepository.deleteById(id);
    }

    /****************************************************************/

    public void anularCuenta(Cuenta cuenta) {
        if (!cuenta.getUsuarios().isEmpty()) {
            List<Usuario> usuarios = new ArrayList<>(cuenta.getUsuarios()); // Copia de usuarios para evitar ConcurrentModificationException

            for (Usuario usuario : usuarios) {
                usuario.getCuentas().remove(cuenta); // Quita la cuenta del usuario
                usuarioRepository.save(usuario);     // Guarda el usuario actualizado sin la cuenta
            }

            cuenta.getUsuarios().clear();           // Limpia la lista de usuarios en la cuenta
            cuenta.setActiva(false);                // Marca la cuenta como inactiva

            cuentaRepository.save(cuenta);          // Guarda la cuenta actualizada
        }
    }


    public Cuenta activarUsuarioEnCuenta(Long cuentaId, Long usuarioId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId).orElse(null);
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (cuenta == null || usuario == null) {
            return null;
        }

        // Asegúrate de que el usuario no esté ya en la lista
        if (!cuenta.getUsuarios().contains(usuario)) {
            cuenta.getUsuarios().add(usuario);
            cuenta.setActiva(Boolean.TRUE);
            usuario.getCuentas().add(cuenta);
            usuarioRepository.save(usuario);
            cuentaRepository.save(cuenta);  // Guardar la cuenta actualizada con el usuario
        }

        return cuenta;
    }

}
