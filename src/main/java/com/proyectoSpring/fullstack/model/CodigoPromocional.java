package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "codigos_promocionales")
public class CodigoPromocional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDescuento tipoDescuento;

    @Column(nullable = false)
    private double valorDescuento;

    @Column(nullable = false)
    private double montoMinimoCompra;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @Column(nullable = false)
    private LocalDateTime fechaFin;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(nullable = false)
    private int usosMaximos;

    @Column(nullable = false)
    private int usosActuales = 0;

    @ManyToOne
    @JoinColumn(name = "creado_por_id")
    private Usuario creadoPor;

    @Column
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    public boolean esValido() {
        LocalDateTime ahora = LocalDateTime.now();
        return activo && 
               ahora.isAfter(fechaInicio) && 
               ahora.isBefore(fechaFin) && 
               usosActuales < usosMaximos;
    }

    public double calcularDescuento(double montoCompra) {
        if (!esValido() || montoCompra < montoMinimoCompra) {
            return 0;
        }

        if (tipoDescuento == TipoDescuento.PORCENTAJE) {
            return montoCompra * (valorDescuento / 100);
        } else {
            return Math.min(valorDescuento, montoCompra);
        }
    }

    public void incrementarUso() {
        if (usosActuales < usosMaximos) {
            usosActuales++;
        }
    }
}

enum TipoDescuento {
    PORCENTAJE,
    MONTO_FIJO
} 