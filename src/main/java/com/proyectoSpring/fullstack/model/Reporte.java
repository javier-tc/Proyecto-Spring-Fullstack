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

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario generadoPor;

    @Column(nullable = false)
    private LocalDateTime fechaGeneracion;

    @Column
    private String rutaArchivo;

    @Column
    private String formato; // PDF, EXCEL, CSV

    @Column
    private String parametros; // JSON con parámetros del reporte

    @PrePersist
    protected void onCreate() {
        fechaGeneracion = LocalDateTime.now();
    }
}

enum TipoReporte {
    VENTAS_DIARIAS,
    VENTAS_MENSUALES,
    INVENTARIO,
    PEDIDOS_PENDIENTES,
    PRODUCTOS_MAS_VENDIDOS,
    CLIENTES_FRECUENTES,
    RENDIMIENTO_SUCURSAL,
    LOGISTICA_ENVIOS
} 