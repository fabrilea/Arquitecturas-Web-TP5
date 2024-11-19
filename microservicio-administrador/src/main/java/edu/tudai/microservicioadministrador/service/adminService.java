package edu.tudai.microservicioadministrador.service;


import edu.tudai.microservicioadministrador.client.MonopatinClient;
import edu.tudai.microservicioadministrador.client.cuentaClient;
import edu.tudai.microservicioadministrador.client.facturaClient;
import edu.tudai.microservicioadministrador.client.tarifaClient;
import edu.tudai.microservicioadministrador.dto.MonopatinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class adminService {

    private final cuentaClient cuentaClient;

    private final MonopatinClient monopatinClient;

    private final facturaClient facturaClient;

    private final tarifaClient tarifaClient;


    public void anularCuenta(long id) {
        cuentaClient.anularCuenta(id);
    }

    public List<MonopatinDTO> obtenerMonopatinesConMasViajes(int minViajes, int anio) {
        return cuentaClient.obtenerMonopatinesConMasViajes(minViajes, anio);
    }

    public Map<String, Long> getVSMonopatin(){
        return monopatinClient.obtenerEstadoMonopatines();
    }

    public double obtenerTotalFacturado(int anio, int mesInicio, int mesFin) {
        return facturaClient.obtenerTotalFacturado(anio, mesInicio, mesFin);
    }

    public void ajustarPrecios(double nuevaTarifaBase, double nuevaTarifaExtra, LocalDate fechaInicio) {
        tarifaClient.ajustarPrecios(nuevaTarifaBase, nuevaTarifaExtra, fechaInicio);
    }

}
