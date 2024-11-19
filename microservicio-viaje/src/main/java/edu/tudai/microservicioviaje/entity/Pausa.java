package edu.tudai.microservicioviaje.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Pausa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime inicio;
    private LocalDateTime fin;
    private long duracion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje;

    public Pausa() {}

    public Pausa(LocalDateTime inicio, LocalDateTime fin, long duracion, Viaje viaje) {
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

    public long getDuracion() {
        return duracion;
    }

    public void setDuracion(long duracion) {
        this.duracion = duracion;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }
}
