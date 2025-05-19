package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    // Buscar notificaciones por usuario
    List<Notificacion> findByUsuarioId(Long usuarioId);
}