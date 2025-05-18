package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "programas_fidelizacion")
public class ProgramaFidelizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "puntos_por_compra", nullable = false)
    private Integer puntosPorCompra;

    @Column(name = "valor_punto", nullable = false)
    private BigDecimal valorPunto;

    @Column(name = "monto_minimo_compra", nullable = false)
    private BigDecimal montoMinimoCompra;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "requisito_puntos_nivel1")
    private Integer requisitoPuntosNivel1;

    @Column(name = "requisito_puntos_nivel2")
    private Integer requisitoPuntosNivel2;

    @Column(name = "requisito_puntos_nivel3")
    private Integer requisitoPuntosNivel3;

    @Column(name = "beneficio_nivel1")
    private String beneficioNivel1;

    @Column(name = "beneficio_nivel2")
    private String beneficioNivel2;

    @Column(name = "beneficio_nivel3")
    private String beneficioNivel3;

    @Column(name = "multiplicador_puntos_nivel1")
    private BigDecimal multiplicadorPuntosNivel1;

    @Column(name = "multiplicador_puntos_nivel2")
    private BigDecimal multiplicadorPuntosNivel2;

    @Column(name = "multiplicador_puntos_nivel3")
    private BigDecimal multiplicadorPuntosNivel3;
} 