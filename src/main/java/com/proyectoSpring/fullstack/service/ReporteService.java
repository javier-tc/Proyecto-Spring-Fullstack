package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.Reporte;
import com.proyectoSpring.fullstack.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {
    private final ReporteRepository reporteRepository;

    @Autowired
    public ReporteService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    public List<Reporte> findAll() {
        return reporteRepository.findAll();
    }

    public Optional<Reporte> findById(Long id) {
        return reporteRepository.findById(id);
    }

    public List<Reporte> findByTipo(String tipo) {
        return reporteRepository.findByTipo(tipo);
    }

    public Reporte save(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public void deleteById(Long id) {
        reporteRepository.deleteById(id);
    }
}