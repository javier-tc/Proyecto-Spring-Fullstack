package com.proyectoSpring.fullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    // Nuevo campo: intentos fallidos de login
    @Column(nullable = false)
    private int intentosFallidos = 0;

    // Nuevo campo: indica si el usuario está bloqueado
    @Column(nullable = false)
    private boolean bloqueado = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();

    @Column(nullable = false)
    private boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;
}