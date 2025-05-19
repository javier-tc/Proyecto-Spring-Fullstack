package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.CarritoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoCompraRepository extends JpaRepository<CarritoCompra, Long> {
    // Buscar carrito por usuario
    Optional<CarritoCompra> findByUsuarioId(Long usuarioId);
}