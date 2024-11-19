package edu.tudai.microserviciofactura.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = false)
    @JsonIgnore
    private Factura factura;

    private Long viajeId;
    private double tarifaBase;
    private double tarifaExtra;
    private long tiempoUso;
    private long tiempoPausado;
    private double montoCalculado;

    public DetalleFactura() {
        super();
    }

    public DetalleFactura(Factura factura, Long viajeId, double tarifaBase, double tarifaExtra, long tiempoUso, long tiempoPausado) {
        this.factura = factura;
        this.viajeId = viajeId;
        this.tarifaBase = tarifaBase;
        this.tarifaExtra = tarifaExtra;
        this.tiempoUso = tiempoUso;
        this.tiempoPausado = tiempoPausado;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
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

    public double getMontoCalculado() {
        return montoCalculado;
    }

    public void calcularMonto() {
        double costoBase = tarifaBase * tiempoUso;
        double costoExtra = tiempoPausado > 15 ? tarifaExtra * (tiempoPausado - 15) : 0;
        this.montoCalculado = costoBase + costoExtra;
    }
}
