package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "telefonos_usuario")
public class TelefonoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTelefono tipoTelefono;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private boolean principal = false;
}

enum TipoTelefono {
    CELULAR,
    FIJO,
    TRABAJO
} 