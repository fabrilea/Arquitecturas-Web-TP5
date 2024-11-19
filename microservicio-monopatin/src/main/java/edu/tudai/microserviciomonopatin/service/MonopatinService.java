package edu.tudai.microserviciomonopatin.service;

import edu.tudai.microserviciomonopatin.client.ViajeClient;
import edu.tudai.microserviciomonopatin.entity.Monopatin;
import edu.tudai.microserviciomonopatin.repository.MonopatinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MonopatinService {
    private final MonopatinRepository monopatinRepository;
    private final ViajeClient viajeClient;

    @Transactional(readOnly = true)
    public List<Monopatin> findAll() {
        return monopatinRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Monopatin findById(Long id) {
        return monopatinRepository.findById(id).orElse(null);
    }

    @Transactional
    public Monopatin save(Monopatin monopatin) {
        return monopatinRepository.save(monopatin);
    }

    @Transactional
    public Monopatin update(Monopatin monopatin) {
        return monopatinRepository.save(monopatin);
    }

    @Transactional
    public void delete(Long id) {
        monopatinRepository.deleteById(id);
    }

    /*******************************************************/

    public Map<String, Long> obtenerEstadoMonopatines() {
        long enOperacion = monopatinRepository.countByDisponibleTrueAndEnMantenimientoFalse();
        long enMantenimiento = monopatinRepository.countByEnMantenimientoTrue();

        return Map.of("En Operación", enOperacion, "En Mantenimiento", enMantenimiento);
    }

    public List<Monopatin> obtenerMonopatinesCercanos(double latitud, double longitud, double radio) {
        return monopatinRepository.findMonopatinesCercanos(latitud, longitud, radio);
    }

    public List<Monopatin> obtenerMonopatinesConMasViajes(int minViajes, int anio) {
        return viajeClient.obtenerMonopatinesConMasViajes(minViajes, anio);
    }

    public List<Monopatin> obtenerMonopatinesKilometros(Double km) {
        return monopatinRepository.findMonopatinesKilometros(km);
    }
}
