package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "metodos_envio")
public class MetodoEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;

    @Column(name = "tiempo_estimado", nullable = false)
    private Integer tiempoEstimado;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "peso_maximo", nullable = false)
    private Double pesoMaximo;

    @Column(name = "dimensiones_maximas")
    private String dimensionesMaximas;

    @Column(name = "requiere_aseguramiento")
    private boolean requiereAseguramiento = false;

    @Column(name = "costo_aseguramiento", precision = 10, scale = 2)
    private BigDecimal costoAseguramiento;
} 