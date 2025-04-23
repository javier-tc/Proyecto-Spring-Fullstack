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
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column(nullable = false)
    private String numeroTransaccion;

    @Column(nullable = false)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado;

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    private String detallesTransaccion;
}

enum MetodoPago {
    TARJETA_CREDITO,
    TARJETA_DEBITO,
    PAYPAL,
    WEBPAY,
    TRANSFERENCIA
}

enum EstadoPago {
    PENDIENTE,
    COMPLETADO,
    RECHAZADO,
    REEMBOLSADO
} 