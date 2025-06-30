package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    List<Auditoria> findByUsuarioId(Long usuarioId);
    List<Auditoria> findByTipoAccion(String tipoAccion);
    List<Auditoria> findByFechaAccionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Auditoria> findByEntidadAfectada(String entidadAfectada);
    List<Auditoria> findByResultado(Boolean resultado);
} 