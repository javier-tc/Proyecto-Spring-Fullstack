package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.Permiso;
import com.proyectoSpring.fullstack.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoService {
    private final PermisoRepository permisoRepository;

    @Autowired
    public PermisoService(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }

    public List<Permiso> findAll() {
        return permisoRepository.findAll();
    }

    public Optional<Permiso> findById(Long id) {
        return permisoRepository.findById(id);
    }

    public Optional<Permiso> findByNombre(String nombre) {
        return permisoRepository.findByNombre(nombre);
    }

    public Permiso save(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    public void deleteById(Long id) {
        permisoRepository.deleteById(id);
    }
}