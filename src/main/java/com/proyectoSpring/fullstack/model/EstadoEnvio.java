package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "estados_envio")
public class EstadoEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEnvioEnum estado;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @Column(nullable = false)
    private String ubicacion;

    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario actualizadoPor;

    @PrePersist
    protected void onCreate() {
        fechaActualizacion = LocalDateTime.now();
    }
}

enum EstadoEnvioEnum {
    PENDIENTE,
    PREPARANDO,
    EN_TRANSITO,
    EN_DISTRIBUCION,
    ENTREGADO,
    DEVUELTO,
}
 