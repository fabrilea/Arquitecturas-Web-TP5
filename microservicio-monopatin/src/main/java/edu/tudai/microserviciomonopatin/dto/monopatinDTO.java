package edu.tudai.microserviciomonopatin.dto;

public class monopatinDTO {

    private double bateria;
    private double latitud;
    private double longitud;
    private double kilometrosRecorridos;
    private double tiempoUso;
    private Long paradaId;
    private double tarifaBase;
    private double tarifaExtraPausa;

    public monopatinDTO() {
        super();
    }

    public monopatinDTO(double bateria, double latitud, double longitud, double kilometrosRecorridos, double tiempoUso, Long paradaId, double tarifaBase, double tarifaExtraPausa) {
        this.bateria = bateria;
        this.latitud = latitud;
        this.longitud = longitud;
        this.kilometrosRecorridos = kilometrosRecorridos;
        this.tiempoUso = tiempoUso;
        this.paradaId = paradaId;
        this.tarifaBase = tarifaBase;
        this.tarifaExtraPausa = tarifaExtraPausa;
    }

    public double getBateria() {
        return bateria;
    }

    public void setBateria(double bateria) {
        this.bateria = bateria;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getKilometrosRecorridos() {
        return kilometrosRecorridos;
    }

    public void setKilometrosRecorridos(double kilometrosRecorridos) {
        this.kilometrosRecorridos = kilometrosRecorridos;
    }

    public double getTiempoUso() {
        return tiempoUso;
    }

    public void setTiempoUso(double tiempoUso) {
        this.tiempoUso = tiempoUso;
    }

    public Long getParadaId() {
        return paradaId;
    }

    public void setParadaId(Long paradaId) {
        this.paradaId = paradaId;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public double getTarifaExtraPausa(){
        return tarifaExtraPausa;
    }

    public void setTarifaExtraPausa(double tarifaExtraPausa) {
        this.tarifaExtraPausa = tarifaExtraPausa;
    }
}
