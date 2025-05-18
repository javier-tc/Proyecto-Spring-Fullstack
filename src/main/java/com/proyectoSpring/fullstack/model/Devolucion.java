package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "devoluciones")
public class Devolucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDateTime fechaDevolucion;

    @Column(nullable = false)
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoDevolucion estado;

    @Column(name = "monto_devolucion", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoDevolucion;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_devolucion", nullable = false)
    private MetodoPago metodoDevolucion;

    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario procesadoPor;

    @Column(name = "fecha_procesamiento")
    private LocalDateTime fechaProcesamiento;

    @PrePersist
    protected void onCreate() {
        fechaDevolucion = LocalDateTime.now();
        estado = EstadoDevolucion.PENDIENTE;
    }
}

enum EstadoDevolucion {
    PENDIENTE,
    APROBADA,
    RECHAZADA,
    COMPLETADA,
    CANCELADA
} 