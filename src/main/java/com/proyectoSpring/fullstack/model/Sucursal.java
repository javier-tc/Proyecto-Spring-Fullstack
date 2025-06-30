package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sucursales")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String telefono;

    @Column
    private String email;

    @Column(nullable = false)
    private boolean activa = true;

    @Column(name = "horario_apertura")
    private String horarioApertura;

    @Column(name = "horario_cierre")
    private String horarioCierre;

    public Sucursal() {}

    public Sucursal(Long id) {
        this.id = id;
    }
} 