package edu.tudai.microserviciousuario.service;

import edu.tudai.microserviciousuario.dto.MonopatinDTO;
import edu.tudai.microserviciousuario.entity.Usuario;
import edu.tudai.microserviciousuario.client.monopatinClient;
import edu.tudai.microserviciousuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final monopatinClient monopatinClient;

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    /**********************************************************/

    public List<MonopatinDTO> obtenerMonopatinesCercanos(double latitud, double longitud, double radio){
        return monopatinClient.obtenerMonopatinesCercanos(latitud, longitud, radio);
    }

}
