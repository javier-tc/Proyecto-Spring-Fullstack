package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    // Buscar permiso por nombre
    Optional<Permiso> findByNombre(String nombre);
}