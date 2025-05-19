package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Buscar productos por categoría
    List<Producto> findByCategoriaId(Long categoriaId);
}