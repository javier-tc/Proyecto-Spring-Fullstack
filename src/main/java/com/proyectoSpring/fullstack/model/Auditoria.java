package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "auditoria")
public class Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_accion", nullable = false)
    private LocalDateTime fechaAccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_accion", nullable = false)
    private TipoAccion tipoAccion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "entidad_afectada", nullable = false)
    private String entidadAfectada;

    @Column(name = "id_entidad")
    private Long idEntidad;

    @Column(name = "detalles", columnDefinition = "TEXT")
    private String detalles;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "resultado", nullable = false)
    private Boolean resultado;

    @Column(name = "mensaje_error")
    private String mensajeError;

    @PrePersist
    protected void onCreate() {
        fechaAccion = LocalDateTime.now();
    }
} 