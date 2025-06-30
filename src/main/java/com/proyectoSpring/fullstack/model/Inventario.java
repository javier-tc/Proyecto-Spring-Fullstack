package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "inventario")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo;

    @Column(name = "stock_maximo", nullable = false)
    private Integer stockMaximo;

    @Column(nullable = false)
    private boolean activo = true;

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