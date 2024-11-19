package edu.tudai.microservicioadministrador.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class PausaDTO {

    private Long id;

    private LocalDateTime inicio;
    private LocalDateTime fin;
    private double duracion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viajeDTO_id", nullable = false)
    private ViajeDTO viaje;

    public PausaDTO() {
    }

    public PausaDTO(Long id, LocalDateTime inicio, LocalDateTime fin, double duracion, ViajeDTO viaje) {
        this.id = id;
        this.inicio = inicio;
        this.fin = fin;
        this.duracion = duracion;
        this.viaje = viaje;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicioPausa(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public ViajeDTO getViaje() {
        return viaje;
    }

    public void setViaje(ViajeDTO viaje) {
        this.viaje = viaje;
    }
}