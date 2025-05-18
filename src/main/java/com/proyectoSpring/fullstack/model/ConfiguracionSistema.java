package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "configuraciones_sistema")
public class ConfiguracionSistema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clave", nullable = false, unique = true)
    private String clave;

    @Column(name = "valor", nullable = false, columnDefinition = "TEXT")
    private String valor;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo_dato", nullable = false)
    private String tipoDato; // STRING, INTEGER, BOOLEAN, JSON, etc.

    @Column(name = "grupo", nullable = false)
    private String grupo; // SEGURIDAD, NOTIFICACIONES, PAGOS, etc.

    @Column(name = "es_publica", nullable = false)
    private Boolean esPublica = false;

    @Column(name = "requiere_permiso", nullable = false)
    private Boolean requierePermiso = true;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "actualizado_por_id")
    private Usuario actualizadoPor;

    @Column(name = "version", nullable = false)
    private Integer version = 1;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
        version++;
    }
} 