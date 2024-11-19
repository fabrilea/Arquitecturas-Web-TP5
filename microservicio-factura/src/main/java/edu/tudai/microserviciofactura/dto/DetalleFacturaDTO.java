package edu.tudai.microserviciofactura.dto;

public class DetalleFacturaDTO {
    private Long facturaId;
    private Long viajeId;
    private double tarifaBase;
    private double tarifaExtra;
    private long tiempoUso;
    private long tiempoPausado;

    // Constructor
    public DetalleFacturaDTO(Long facturaId, Long viajeId, double tarifaBase, double tarifaExtra, long tiempoUso, long tiempoPausado) {
        this.facturaId = facturaId;
        this.viajeId = viajeId;
        this.tarifaBase = tarifaBase;
        this.tarifaExtra = tarifaExtra;
        this.tiempoUso = tiempoUso;
        this.tiempoPausado = tiempoPausado;
    }

    // Getters y Setters
    public Long getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
    }

    public Long getViajeId() {
        return viajeId;
    }

    public void setViajeId(Long viajeId) {
        this.viajeId = viajeId;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public double getTarifaExtra() {
        return tarifaExtra;
    }

    public void setTarifaExtra(double tarifaExtra) {
        this.tarifaExtra = tarifaExtra;
    }

    public long getTiempoUso() {
        return tiempoUso;
    }

    public void setTiempoUso(long tiempoUso) {
        this.tiempoUso = tiempoUso;
    }

    public long getTiempoPausado() {
        return tiempoPausado;
    }

    public void setTiempoPausado(long tiempoPausado) {
        this.tiempoPausado = tiempoPausado;
    }
}

