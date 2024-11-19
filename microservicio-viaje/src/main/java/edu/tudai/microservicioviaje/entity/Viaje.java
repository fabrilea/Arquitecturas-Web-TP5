package edu.tudai.microservicioviaje.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long monopatinId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private double kilometrosRecorridos;
    private boolean enCurso;

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("viaje")
    private List<Pausa> pausas;

    public Viaje() {
        super();
        this.pausas = new ArrayList<>();
    }

    public Viaje(Long monopatinId, LocalDateTime fechaInicio, LocalDateTime fechaFin, double kilometrosRecorridos) {
        super();
        this.monopatinId = monopatinId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.kilometrosRecorridos = kilometrosRecorridos;
        this.enCurso = true;
    }


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public Long getMonopatinId() {
        return monopatinId;
    }

    public void setMonopatinId(Long monopatinId) {
        this.monopatinId = monopatinId;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getKilometrosRecorridos() {
        return kilometrosRecorridos;
    }

    public void setKilometrosRecorridos(double kilometrosRecorridos) {
        this.kilometrosRecorridos = kilometrosRecorridos;
    }

    public boolean isEnCurso() {
        return enCurso;
    }

    public void setEnCurso(boolean enCurso) {
        this.enCurso = enCurso;
    }

    public List<Pausa> getPausas() {
        return pausas;
    }

    public double getTiempoUso() {
        if (fechaInicio != null && fechaFin != null) {
            // Calcula la duración en minutos (puedes ajustarlo según tus necesidades)
            return Duration.between(fechaInicio, fechaFin).toMinutes();
        }
        return 0; // Retorna 0 si alguna de las fechas es nula
    }
}
