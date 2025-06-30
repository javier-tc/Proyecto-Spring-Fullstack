package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.Notificacion;
import com.proyectoSpring.fullstack.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {
    private final NotificacionRepository notificacionRepository;

    @Autowired
    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public List<Notificacion> findAll() {
        return notificacionRepository.findAll();
    }

    public Optional<Notificacion> findById(Long id) {
        return notificacionRepository.findById(id);
    }

    public List<Notificacion> findByUsuarioId(Long usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId);
    }

    public List<Notificacion> findNoLeidasByUsuarioId(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdAndLeidaFalse(usuarioId);
    }

    public List<Notificacion> findByTipo(String tipo) {
        return notificacionRepository.findByTipoAndActivaTrue(tipo);
    }

    public Notificacion save(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    public Optional<Notificacion> marcarComoLeida(Long id) {
        return notificacionRepository.findById(id)
                .map(notificacion -> {
                    notificacion.setLeida(true);
                    notificacion.setFechaLectura(LocalDateTime.now());
                    return notificacionRepository.save(notificacion);
                });
    }

    public void deleteById(Long id) {
        notificacionRepository.deleteById(id);
    }
}