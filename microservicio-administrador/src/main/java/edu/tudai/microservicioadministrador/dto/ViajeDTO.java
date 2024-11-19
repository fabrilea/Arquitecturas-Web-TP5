package edu.tudai.microservicioadministrador.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ViajeDTO {
    private Long id;

    private Long monopatinId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private double kilometrosRecorridos;
    private boolean enCurso;

    @OneToMany(mappedBy = "viajeDTO", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("viajeDTO")
    private List<PausaDTO> pausas;

    public ViajeDTO(Long id, Long monopatinId, LocalDateTime fechaInicio, LocalDateTime fechaFin, double kilometrosRecorridos, boolean enCurso) {
        this.id = id;
        this.monopatinId = monopatinId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.enCurso = enCurso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<PausaDTO> getPausas(){
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
