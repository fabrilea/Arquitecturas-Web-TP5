package edu.tudai.microservicioadministrador.client;


import edu.tudai.microservicioadministrador.dto.MonopatinDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio-usuario", url = "http://localhost:8001/api/cuenta")
public interface cuentaClient {

    @PutMapping("/anularCuenta/{id}")
    void anularCuenta(@PathVariable("id") long id);

    @GetMapping("/viajes/{minViajes}/{anio}")
    List<MonopatinDTO> obtenerMonopatinesConMasViajes(@PathVariable("minViajes") int minViajes, @PathVariable("anio") int anio);

}
