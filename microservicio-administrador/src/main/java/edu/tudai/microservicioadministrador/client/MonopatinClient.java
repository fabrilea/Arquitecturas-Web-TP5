package edu.tudai.microservicioadministrador.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8002/api/monopatin")
public interface MonopatinClient {

    @PutMapping("/{id}/disponibilidad")
    void actualizarDisponibilidad(@PathVariable("id") Long id, @RequestParam("disponible") boolean disponible);

    @PutMapping("/{id}/mantenimiento")
    void actualizarEnMantenimiento(@PathVariable("id") Long id, @RequestParam("enMantenimiento") boolean disponible);

    @GetMapping("/estado")
    Map<String, Long> obtenerEstadoMonopatines();
}
