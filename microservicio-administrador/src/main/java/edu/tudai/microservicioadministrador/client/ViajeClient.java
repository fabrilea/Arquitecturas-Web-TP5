package edu.tudai.microservicioadministrador.client;

import edu.tudai.microservicioadministrador.dto.ViajeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "microservicio-viaje", url = "http://localhost:8005/api/viaje")
public interface ViajeClient {

    @GetMapping("")
    List<ViajeDTO> getAllViajes();

}

