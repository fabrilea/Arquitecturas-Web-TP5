package edu.tudai.microservicioadministrador.dto;

import jakarta.persistence.*;

public class MonopatinDTO {

    private Long id;

    private double bateria;
    private boolean disponible;
    private boolean enMantenimiento;
    private double latitud;
    private double longitud;
    private double kilometrosRecorridos;
    private double tiempoUso;

    @ManyToOne
    @JoinColumn(name = "parada_id", nullable = false)
    private ParadaDTO parada;


    public MonopatinDTO() {
        super();
    }

    public MonopatinDTO(Long id, Double latitud, Double longitud, Double bateria, double kilometrosRecorridos, double tiempoUso, ParadaDTO parada) {
        super();
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.bateria = bateria;
        this.disponible = true;
        this.enMantenimiento = false;
        this.kilometrosRecorridos = kilometrosRecorridos;
        this.tiempoUso = tiempoUso;
        this.parada = parada;
    }

    public Long getId() {
        return id;
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

    public Double getBateria() {
        return bateria;
    }

    public void setBateria(Double bateria) {
        this.bateria = bateria;
    }

    public boolean isDisponible(){
        return disponible;
    }

    public void setDisponible(boolean disponible){
        this.disponible = disponible;
    }

    public boolean isEnMantenimiento(){
        return enMantenimiento;
    }

    public void setEnMantenimiento(boolean enMantenimiento){
        this.enMantenimiento = enMantenimiento;
    }

    public double getKilometrosRecorridos(){
        return kilometrosRecorridos;
    }

    public void setKilometrosRecorridos(double kilometros){
        this.kilometrosRecorridos = kilometros;
    }

    public double getTiempoUso(){
        return tiempoUso;
    }

    public void setTiempoUso(double tiempo){
        this.tiempoUso = tiempo;
    }

    public ParadaDTO getParada(){
        return parada;
    }

    public void setParada(ParadaDTO parada){
        this.parada = parada;
    }
}
