package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.Reporte;
import com.proyectoSpring.fullstack.model.TipoReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    // Buscar reportes por tipo
    List<Reporte> findByTipo(TipoReporte tipo);
}