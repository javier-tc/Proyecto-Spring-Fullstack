package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    // Buscar facturas por usuario
    List<Factura> findByUsuarioId(Long usuarioId);
}