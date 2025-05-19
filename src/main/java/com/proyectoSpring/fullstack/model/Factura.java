package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroFactura;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario cliente;

    @Column(nullable = false)
    private LocalDateTime fechaEmision;

    @Column(nullable = false)
    private double subtotal;

    @Column(nullable = false)
    private double impuestos;

    @Column(nullable = false)
    private double descuentos;

    @Column(nullable = false)
    private double total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoFactura estado;

    @Column
    private String ruc;

    @Column
    private String razonSocial;

    @Column
    private String direccionFiscal;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<DetalleFactura> detalles = new ArrayList<>();

    @Column
    private String rutaArchivoPDF;

    @Column
    private String rutaArchivoXML;

    @PrePersist
    protected void onCreate() {
        fechaEmision = LocalDateTime.now();
        estado = EstadoFactura.EMITIDA;
        generarNumeroFactura();
    }

    private void generarNumeroFactura() {
        // Formato: F001-001-00000001
        this.numeroFactura = String.format("F001-001-%08d", this.id);
    }

    public void calcularTotales() {
        this.subtotal = detalles.stream()
            .mapToDouble(DetalleFactura::getSubtotal)
            .sum();
        
        this.impuestos = this.subtotal * 0.18; // 18% IGV
        this.total = this.subtotal + this.impuestos - this.descuentos;
    }

    public void agregarDetalle(Producto producto, int cantidad, double precioUnitario) {
        DetalleFactura detalle = new DetalleFactura(this, producto, cantidad, precioUnitario);
        detalles.add(detalle);
        calcularTotales();
    }
}

@Entity
@Table(name = "detalles_factura")
@Data
class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private double precioUnitario;

    @Column(nullable = false)
    private double subtotal;

    public DetalleFactura() {}

    public DetalleFactura(Factura factura, Producto producto, Integer cantidad, double precioUnitario) {
        this.factura = factura;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = precioUnitario * cantidad;
    }
}

enum EstadoFactura {
    EMITIDA,
    ANULADA,
    PAGADA,
    VENCIDA
} 