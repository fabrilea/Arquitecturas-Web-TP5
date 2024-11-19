package edu.tudai.microservicioviaje.service;

import edu.tudai.microservicioviaje.dto.ReporteKilometrosDTO;
import edu.tudai.microservicioviaje.entity.Pausa;
import edu.tudai.microservicioviaje.entity.Viaje;
import edu.tudai.microservicioviaje.repository.ViajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;

    private static long tiempMaxPausa = 15;
    private static double tarifaExtraPorMinuto = 10.0;
    private static double costoKilometro = 7.5;

    @Transactional(readOnly = true)
    public List<Viaje> findAll() {
        return viajeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Viaje findById(Long id) {
        return viajeRepository.findById(id).orElse(null);
    }

    @Transactional
    public Viaje save(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    @Transactional
    public void delete(Long id) {
        viajeRepository.deleteById(id);
    }

    @Transactional
    public Viaje update(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    /****************************************/

    @Transactional(readOnly = true)
    public Double calcularTiempoTotalConPausas(Long viajeId) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        double totalPausaMinutos = 0;

        for (Pausa pausa : viaje.getPausas()) {
            if (pausa.getFin() != null) { // Ignorar pausas en curso
                long minutos = Duration.between(pausa.getInicio(), pausa.getFin()).toMinutes();
                totalPausaMinutos += minutos;
            }
        }

        // Retornar tiempo total incluyendo pausas
        return viaje.getTiempoUso() + totalPausaMinutos;
    }

    @Transactional
    public Viaje finalizarViaje(Long viajeId, double kilometrosRecorridos) {
        // Buscar el viaje por ID
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        // Verificar si el viaje ya está finalizado
        if (!viaje.isEnCurso()) {
            throw new RuntimeException("El viaje ya ha sido finalizado");
        }

        // Establecer la fecha y hora de finalización
        viaje.setFechaFin(LocalDateTime.now());
        viaje.setKilometrosRecorridos(kilometrosRecorridos);
        viaje.setEnCurso(false);

        // Guardar cambios
        viajeRepository.save(viaje);

        return viaje;
    }


    public List<ReporteKilometrosDTO> generarReporteKilometros() {
        // Consultar todos los viajes y agrupar por monopatinId, sumando los kilómetros
        Map<Long, Double> kilometrosPorMonopatin = new HashMap<>();

        List<Viaje> viajes = viajeRepository.findAll();

        for (Viaje viaje : viajes) {
            kilometrosPorMonopatin.merge(viaje.getMonopatinId(), viaje.getKilometrosRecorridos(), Double::sum);
        }

        // Convertir el mapa a una lista de DTOs
        List<ReporteKilometrosDTO> reportes = new ArrayList<>();
        for (Map.Entry<Long, Double> entry : kilometrosPorMonopatin.entrySet()) {
            reportes.add(new ReporteKilometrosDTO(entry.getKey(), entry.getValue()));
        }

        return reportes;
    }



    //para el costo viaje hay que encontrar la forma de hacer que el localtime de inicio de la pausa
    //se le sumen 15 minutos que esta hecho, y transformarlo a double para que el tiempo se pueda cobrar
    // se puede hacer con los siguientes calculos:
    // total += viaje.getTiempoUso() - tiempo(antes de la pausa) * costoKilometro;
    // total += viaje.getTiempoUso() -(viaje.getTiempoUso() - tiempo(antes de la pausa)) * tarifaExtra;
    // no encotre la forma de lo otro por eso suerte a quien lo intente
    public double calcularCostoViaje(long viajeId) {
        Viaje viaje = viajeRepository.findById(viajeId).orElseThrow(() -> new RuntimeException("viaje no encontrado"));
        double total = 0.0;

        if (debeAplicarTarifaExtra(viaje)) {
            Duration tiempo = obtenerDuracionPausaAumentada(viaje.getPausas());

            if (tiempo != null) {
                double minutos = tiempo.toMinutes() + (tiempo.getSeconds() % 60) / 60.0;
                total += minutos * tarifaExtraPorMinuto; //
            }
        }

        total += viaje.getTiempoUso() * costoKilometro;
        return total;
    }

    public boolean debeAplicarTarifaExtra(Viaje viaje) {
        return viaje.getPausas().stream().anyMatch(pausa -> pausa.getDuracion() > tiempMaxPausa);
    }

    public Duration obtenerDuracionPausaAumentada(List<Pausa> pausas) {
        for (Pausa pausa : pausas) {
            if (pausa.getDuracion() > tiempMaxPausa) {
                return Duration.ofMinutes(pausa.getDuracion()).plusMinutes(15); // Agrega 15 minutos a la duración de la pausa
            }
        }
        return null; // Retorna null si no se encuentra ninguna pausa que exceda la duración máxima
    }


}
