package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "evaluaciones_producto")
public class EvaluacionProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private Integer calificacion; // 1-5 estrellas

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "fecha_evaluacion", nullable = false)
    private LocalDateTime fechaEvaluacion;

    @Column(name = "fecha_compra", nullable = false)
    private LocalDateTime fechaCompra;

    @Column(name = "verificada_compra", nullable = false)
    private Boolean verificadaCompra = false;

    @Column(name = "likes")
    private Integer likes = 0;

    @Column(name = "reportada")
    private Boolean reportada = false;

    @Column(name = "motivo_reporte")
    private String motivoReporte;

    @Column(name = "fecha_moderacion")
    private LocalDateTime fechaModeracion;

    @ManyToOne
    @JoinColumn(name = "moderado_por_id")
    private Usuario moderadoPor;

    @PrePersist
    protected void onCreate() {
        fechaEvaluacion = LocalDateTime.now();
    }
} 