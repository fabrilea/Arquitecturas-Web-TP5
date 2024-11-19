package edu.tudai.microservicioadministrador.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

@FeignClient(name = "microservicio-factura", url = "http://localhost:8004/api/tarifa")
public interface tarifaClient {

    @PostMapping("/ajustar-precios")
    void ajustarPrecios(@RequestParam double nuevaTarifaBase, @RequestParam double nuevaTarifaExtra, @RequestParam LocalDate fechaInicio);

}
