package edu.tudai.microservicioviaje.service;

import edu.tudai.microservicioviaje.entity.Pausa;
import edu.tudai.microservicioviaje.repository.PausaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PausaService {

    private final PausaRepository pausaRepository;

    public List<Pausa> findAll() {
        return pausaRepository.findAll();
    }

    public Pausa findById(Long id) {
        return pausaRepository.findById(id).orElse(null);
    }

    public Pausa save(Pausa pausa) {
        return pausaRepository.save(pausa);
    }

    public Pausa update(Long id, Pausa pausaDetails) {
        Pausa existingPausa = findById(id);
        if (existingPausa != null) {
            existingPausa.setInicioPausa(pausaDetails.getInicio());
            existingPausa.setFin(pausaDetails.getFin());
            existingPausa.setDuracion(pausaDetails.getDuracion());
            return pausaRepository.save(existingPausa);
        }
        return null;
    }

    public void delete(Long id) {
        pausaRepository.deleteById(id);
    }
}
