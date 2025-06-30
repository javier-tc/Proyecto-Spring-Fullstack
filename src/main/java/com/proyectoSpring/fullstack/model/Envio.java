package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "envios")
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "metodo_envio_id", nullable = false)
    private MetodoEnvio metodoEnvio;

    @ManyToOne
    @JoinColumn(name = "repartidor_id")
    private Usuario repartidor;

    @Column(nullable = false)
    private String estado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "fecha_entrega_estimada")
    private LocalDateTime fechaEntregaEstimada;

    @Column(name = "fecha_entrega_real")
    private LocalDateTime fechaEntregaReal;

    @Column(name = "costo_envio", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoEnvio;

    @Column(name = "peso_total", nullable = false)
    private Double pesoTotal;

    @Column(name = "dimensiones")
    private String dimensiones;

    @Column(name = "direccion_origen", nullable = false)
    private String direccionOrigen;

    @Column(name = "direccion_destino", nullable = false)
    private String direccionDestino;

    @Column(name = "codigo_seguimiento")
    private String codigoSeguimiento;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "firma_entrega")
    private String firmaEntrega;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        estado = "PENDIENTE";
    }
} 