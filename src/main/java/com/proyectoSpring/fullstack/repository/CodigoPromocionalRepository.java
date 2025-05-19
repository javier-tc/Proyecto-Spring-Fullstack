package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.CodigoPromocional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodigoPromocionalRepository extends JpaRepository<CodigoPromocional, Long> {
    // Buscar código promocional por código
    Optional<CodigoPromocional> findByCodigo(String codigo);
}