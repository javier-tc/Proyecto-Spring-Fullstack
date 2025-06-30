package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.Reporte;
import com.proyectoSpring.fullstack.model.TipoReporte;
import com.proyectoSpring.fullstack.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return reporteRepository.findByTipo(TipoReporte.valueOf(tipo.toUpperCase()));
    }

    public List<Reporte> findByUsuarioId(Long usuarioId) {
        return reporteRepository.findByGeneradoPorId(usuarioId);
    }

    public Reporte save(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public Reporte generarReporte(Reporte reporte) {
        reporte.setFechaGeneracion(LocalDateTime.now());
        return reporteRepository.save(reporte);
    }

    public Reporte generarEstadisticasVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Reporte reporte = new Reporte();
        reporte.setTipo(TipoReporte.VENTAS);
        reporte.setFechaGeneracion(LocalDateTime.now());
        reporte.setParametros("{\"fechaInicio\":\"" + fechaInicio + "\",\"fechaFin\":\"" + fechaFin + "\"}");
        return reporteRepository.save(reporte);
    }

    public Reporte generarEstadisticasInventario() {
        Reporte reporte = new Reporte();
        reporte.setTipo(TipoReporte.INVENTARIO);
        reporte.setFechaGeneracion(LocalDateTime.now());
        return reporteRepository.save(reporte);
    }

    public Reporte generarEstadisticasUsuarios() {
        Reporte reporte = new Reporte();
        reporte.setTipo(TipoReporte.USUARIOS);
        reporte.setFechaGeneracion(LocalDateTime.now());
        return reporteRepository.save(reporte);
    }

    public void deleteById(Long id) {
        reporteRepository.deleteById(id);
    }
}