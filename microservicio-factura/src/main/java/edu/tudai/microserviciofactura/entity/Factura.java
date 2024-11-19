package edu.tudai.microserviciofactura.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;
    private double montoTotal;
    private LocalDate fechaEmision;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detallesFactura;

    public Factura() {
        this.detallesFactura = new ArrayList<>();
    }

    public Factura(Long usuarioId, double montoTotal, LocalDate fechaEmision) {
        this();
        this.usuarioId = usuarioId;
        this.montoTotal = montoTotal;
        this.fechaEmision = fechaEmision;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public List<DetalleFactura> getDetallesFactura() {
        return detallesFactura;
    }

    /**
     * Calcula el monto total sumando el monto calculado de cada detalle.
     */
    public double calcularMontoTotal() {
        return detallesFactura.stream()
                .mapToDouble(DetalleFactura::getMontoCalculado)
                .sum();
    }

    /**
     * Agrega un detalle a la factura y actualiza el monto total.
     */
    public void agregarDetalle(DetalleFactura detalle) {
        detalle.setFactura(this);  // Establecer la relación bidireccional
        detallesFactura.add(detalle);
        recalcularMontoTotal();
    }

    /**
     * Recalcula el monto total basado en los detalles de la factura.
     */
    public void recalcularMontoTotal() {
        this.montoTotal = calcularMontoTotal();
    }
}
