package edu.tudai.microservicioviaje.dto;

public class ReporteKilometrosDTO {
    private Long monopatinId;
    private double kilometrosTotales;

    public ReporteKilometrosDTO(Long monopatinId, double kilometrosTotales) {
        this.monopatinId = monopatinId;
        this.kilometrosTotales = kilometrosTotales;
    }

    // Getters y setters
    public Long getMonopatinId() {
        return monopatinId;
    }

    public void setMonopatinId(Long monopatinId) {
        this.monopatinId = monopatinId;
    }

    public double getKilometrosTotales() {
        return kilometrosTotales;
    }

    public void setKilometrosTotales(double kilometrosTotales) {
        this.kilometrosTotales = kilometrosTotales;
    }
}
