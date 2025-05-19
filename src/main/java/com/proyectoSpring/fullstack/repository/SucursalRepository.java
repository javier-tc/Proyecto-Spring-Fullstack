package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    // Buscar sucursal por ciudad
    Optional<Sucursal> findByCiudad(String ciudad);
}