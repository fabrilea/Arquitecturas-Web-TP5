package edu.tudai.microserviciousuario.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fechaAlta;
    private Double saldo;
    private boolean activa;

    @ManyToMany
    @JsonIgnore
    private List<Usuario> usuarios;

    public Cuenta() {
        super();
        this.usuarios = new ArrayList<Usuario>();
    }

    public Cuenta(String fechaAlta, Double saldo) {
        this.fechaAlta = fechaAlta;
        this.saldo = saldo;
        this.activa = true;
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}
