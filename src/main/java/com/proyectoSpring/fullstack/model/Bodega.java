package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bodegas")
public class Bodega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    @Column(name = "capacidad_total", nullable = false)
    private Double capacidadTotal;

    @Column(name = "capacidad_utilizada", nullable = false)
    private Double capacidadUtilizada = 0.0;

    @Column(name = "temperatura_controlada")
    private Boolean temperaturaControlada = false;

    @Column(name = "temperatura_minima")
    private Double temperaturaMinima;

    @Column(name = "temperatura_maxima")
    private Double temperaturaMaxima;

    @Column(name = "humedad_controlada")
    private Boolean humedadControlada = false;

    @Column(name = "humedad_minima")
    private Double humedadMinima;

    @Column(name = "humedad_maxima")
    private Double humedadMaxima;

    @Column(name = "ultima_inspeccion")
    private LocalDateTime ultimaInspeccion;

    @Column(name = "proxima_inspeccion")
    private LocalDateTime proximaInspeccion;

    @Column(name = "estado_operativo", nullable = false)
    private Boolean estadoOperativo = true;

    @Column(name = "observaciones")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Usuario responsable;
} 