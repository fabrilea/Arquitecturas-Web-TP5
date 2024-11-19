package edu.tudai.microserviciomonopatin.service;

import edu.tudai.microserviciomonopatin.entity.Parada;
import edu.tudai.microserviciomonopatin.repository.ParadaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ParadaService {

    private final ParadaRepository paradaRepository;

    @Transactional(readOnly = true)
    public List<Parada> findAll() {
        return paradaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Parada findById(Long id) {
        return paradaRepository.findById(id).orElse(null);
    }

    @Transactional
    public Parada save(Parada parada) {
        return paradaRepository.save(parada);
    }

    @Transactional
    public void delete(Long id) {
        paradaRepository.deleteById(id);
    }

    @Transactional
    public Parada update(Parada parada) {
        return paradaRepository.save(parada);
    }
}
