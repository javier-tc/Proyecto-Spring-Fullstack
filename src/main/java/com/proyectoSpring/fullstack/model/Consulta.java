package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_consulta", nullable = false)
    private TipoConsulta tipoConsulta;

    @Column(nullable = false)
    private String asunto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoConsulta estado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_respuesta")
    private LocalDateTime fechaRespuesta;

    @Column(columnDefinition = "TEXT")
    private String respuesta;

    @ManyToOne
    @JoinColumn(name = "atendido_por_id")
    private Usuario atendidoPor;

    @Column(name = "prioridad", nullable = false)
    private Integer prioridad = 3; // 1: Alta, 2: Media, 3: Baja

    @Column(name = "tiempo_respuesta")
    private Integer tiempoRespuesta; // en horas

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        estado = EstadoConsulta.PENDIENTE;
    }
}

enum TipoConsulta {
    PRODUCTO,
    PEDIDO,
    FACTURACION,
    RECLAMACION,
    SUGERENCIA,
    GENERAL
}

enum EstadoConsulta {
    PENDIENTE,
    EN_PROCESO,
    RESUELTA,
    CERRADA,
    CANCELADA
} 