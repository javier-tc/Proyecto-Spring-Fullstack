package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "numero_transaccion", nullable = false)
    private String numeroTransaccion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "detalles_transaccion")
    private String detallesTransaccion;

    @PrePersist
    protected void onCreate() {
        fechaPago = LocalDateTime.now();
        estado = EstadoPago.PENDIENTE;
    }
}

enum EstadoPago {
    PENDIENTE,
    COMPLETADO,
    RECHAZADO,
    REEMBOLSADO
} 