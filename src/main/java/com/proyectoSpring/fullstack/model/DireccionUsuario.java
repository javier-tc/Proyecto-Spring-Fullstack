package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "direcciones_usuario")
public class DireccionUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDireccion tipoDireccion;

    @Column(nullable = false)
    private String calle;

    @Column(nullable = false)
    private String numero;

    private String departamento;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String codigoPostal;

    @Column(nullable = false)
    private String pais;

    @Column(nullable = false)
    private boolean principal = false;
}

enum TipoDireccion {
    FACTURACION,
    ENVIO
} 