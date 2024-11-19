package edu.tudai.microservicioadministrador.dto;


import jakarta.persistence.*;
import java.time.LocalDate;

public class TarifaDTO {

    public enum TipoTarifa {
        BASE,
        EXTRA_PAUSA
    }

    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoTarifa tipo;

    private double monto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public TarifaDTO() {
        super();
    }

    public TarifaDTO(Long id, TipoTarifa tipo, double monto, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    // Constructor, Getters y Setters

    public Long getId() {
        return id;
    }

    public TipoTarifa getTipo() {
        return tipo;
    }

    public void setTipo(TipoTarifa tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
