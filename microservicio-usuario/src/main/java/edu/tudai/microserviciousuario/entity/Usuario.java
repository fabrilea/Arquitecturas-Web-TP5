package edu.tudai.microserviciousuario.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity  // Esto marca la clase como una entidad JPA
public class Usuario {

    @Id  // Usar la anotación de JPA para la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String celular;
    private String email;

    @ManyToMany
    private List<Cuenta> cuentas;

    public Usuario() {
        super();
        this.cuentas = new ArrayList<Cuenta>();
    }

    public Usuario(String nombre, String apellido, String celular, String email) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }
}
