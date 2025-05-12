package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "inventarios")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Integer stockMinimo;

    @Column(nullable = false)
    private Integer stockMaximo;

    @Column
    private String ubicacionEstante;

    public void ajustarStock(Integer cantidad) {
        this.cantidad += cantidad;
    }

    public boolean verificarDisponibilidad(Integer cantidadSolicitada) {
        return this.cantidad >= cantidadSolicitada;
    }

    public boolean necesitaReposicion() {
        return this.cantidad <= this.stockMinimo;
    }
} 