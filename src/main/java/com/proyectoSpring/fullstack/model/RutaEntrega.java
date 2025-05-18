package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "rutas_entrega")
public class RutaEntrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "repartidor_id", nullable = false)
    private Usuario repartidor;

    @Column(name = "fecha_ruta", nullable = false)
    private LocalDateTime fechaRuta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoRuta estado;

    @Column(name = "hora_inicio")
    private LocalDateTime horaInicio;

    @Column(name = "hora_fin")
    private LocalDateTime horaFin;

    @Column(name = "distancia_total")
    private Double distanciaTotal;

    @Column(name = "tiempo_estimado")
    private Integer tiempoEstimado; // en minutos

    @Column(name = "combustible_estimado")
    private Double combustibleEstimado;

    @Column(name = "observaciones")
    private String observaciones;

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    @Column(name = "vehiculo_asignado")
    private String vehiculoAsignado;

    @Column(name = "capacidad_vehiculo")
    private Double capacidadVehiculo;

    @Column(name = "peso_total")
    private Double pesoTotal;

    @PrePersist
    protected void onCreate() {
        estado = EstadoRuta.PLANIFICADA;
    }
}

enum EstadoRuta {
    PLANIFICADA,
    EN_PROGRESO,
    COMPLETADA,
    CANCELADA,
    RETRASADA
} 