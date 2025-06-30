package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.Auditoria;
import com.proyectoSpring.fullstack.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuditoriaService {
    private final AuditoriaRepository auditoriaRepository;

    @Autowired
    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    public List<Auditoria> findAll() {
        return auditoriaRepository.findAll();
    }

    public Optional<Auditoria> findById(Long id) {
        return auditoriaRepository.findById(id);
    }

    public List<Auditoria> findByUsuarioId(Long usuarioId) {
        return auditoriaRepository.findByUsuarioId(usuarioId);
    }

    public List<Auditoria> findByTipoAccion(String tipoAccion) {
        return auditoriaRepository.findByTipoAccion(tipoAccion);
    }

    public List<Auditoria> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return auditoriaRepository.findByFechaAccionBetween(fechaInicio, fechaFin);
    }

    public List<Auditoria> findByEntidad(String entidad) {
        return auditoriaRepository.findByEntidadAfectada(entidad);
    }

    public Auditoria save(Auditoria auditoria) {
        return auditoriaRepository.save(auditoria);
    }

    public void deleteById(Long id) {
        auditoriaRepository.deleteById(id);
    }
} 