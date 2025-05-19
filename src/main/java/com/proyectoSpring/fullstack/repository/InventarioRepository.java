package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    // Buscar inventario por sucursal
    List<Inventario> findBySucursalId(Long sucursalId);
}