package edu.tudai.microserviciousuario.dto;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class ParadaDTO {

    private String nombre;
    private double latitud;
    private double longitud;

    @OneToMany(mappedBy = "parada")
    private List<MonopatinDTO> monopatines;



    public ParadaDTO() {
        super();
        this.monopatines = new ArrayList<>();
    }

    public ParadaDTO(String nombre, Double latitud, Double longitud) {
        super();
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public List<MonopatinDTO> getMonopatines() {
        return monopatines;
    }


}

