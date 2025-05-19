package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Buscar categoría por nombre
    Optional<Categoria> findByNombre(String nombre);
}