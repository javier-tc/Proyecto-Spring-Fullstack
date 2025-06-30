package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoReporte tipo;

    @Column(name = "fecha_generacion", nullable = false)
    private LocalDateTime fechaGeneracion;

    @ManyToOne
    @JoinColumn(name = "generado_por_id")
    private Usuario generadoPor;

    @Column(name = "parametros", columnDefinition = "TEXT")
    private String parametros;

    @Column(name = "resultado", columnDefinition = "TEXT")
    private String resultado;

    @Column(name = "estado", nullable = false)
    private String estado = "GENERADO";

    @Column(name = "formato")
    private String formato;

    @Column(name = "tamaño_archivo")
    private Long tamañoArchivo;

    @Column(name = "url_descarga")
    private String urlDescarga;

    @PrePersist
    protected void onCreate() {
        fechaGeneracion = LocalDateTime.now();
    }
} 