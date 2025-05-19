package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.Rol;
import com.proyectoSpring.fullstack.model.TipoRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(TipoRol nombre);
} 