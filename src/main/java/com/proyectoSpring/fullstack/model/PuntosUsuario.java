package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "puntos_usuario")
public class PuntosUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "programa_id", nullable = false)
    private ProgramaFidelizacion programa;

    @Column(name = "puntos_acumulados", nullable = false)
    private Integer puntosAcumulados = 0;

    @Column(name = "puntos_canjeados", nullable = false)
    private Integer puntosCanjeados = 0;

    @Column(name = "puntos_vencidos", nullable = false)
    private Integer puntosVencidos = 0;

    @Column(name = "nivel_actual", nullable = false)
    private Integer nivelActual = 1;

    @Column(name = "fecha_ultima_actualizacion", nullable = false)
    private LocalDateTime fechaUltimaActualizacion;

    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;

    @Column(name = "ultima_compra")
    private LocalDateTime ultimaCompra;

    @Column(name = "total_compras", nullable = false)
    private Integer totalCompras = 0;

    @Column(name = "monto_total_compras", nullable = false)
    private Double montoTotalCompras = 0.0;

    @PrePersist
    protected void onCreate() {
        fechaUltimaActualizacion = LocalDateTime.now();
    }
} 