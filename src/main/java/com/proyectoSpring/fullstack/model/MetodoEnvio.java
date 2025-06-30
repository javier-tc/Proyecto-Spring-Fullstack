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

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "costo_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoBase;

    @Column(name = "tiempo_estimado")
    private String tiempoEstimado;

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