package com.proyectoSpring.fullstack.controller;

import com.proyectoSpring.fullstack.assembler.ReporteAssembler;
import com.proyectoSpring.fullstack.model.Reporte;
import com.proyectoSpring.fullstack.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {
    private final ReporteService reporteService;
    private final ReporteAssembler reporteAssembler;

    @Autowired
    public ReporteController(ReporteService reporteService, ReporteAssembler reporteAssembler) {
        this.reporteService = reporteService;
        this.reporteAssembler = reporteAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Reporte>> getAllReportes() {
        List<EntityModel<Reporte>> reportes = reporteService.findAll().stream()
                .map(reporteAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(reportes,
                linkTo(methodOn(ReporteController.class).getAllReportes()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Reporte>> getReporteById(@PathVariable Long id) {
        return reporteService.findById(id)
                .map(reporteAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public CollectionModel<EntityModel<Reporte>> getReportesByTipo(@PathVariable String tipo) {
        List<EntityModel<Reporte>> reportes = reporteService.findByTipo(tipo).stream()
                .map(reporteAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(reportes,
                linkTo(methodOn(ReporteController.class).getReportesByTipo(tipo)).withSelfRel());
    }

    @GetMapping("/usuario/{usuarioId}")
    public CollectionModel<EntityModel<Reporte>> getReportesByUsuario(@PathVariable Long usuarioId) {
        List<EntityModel<Reporte>> reportes = reporteService.findByUsuarioId(usuarioId).stream()
                .map(reporteAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(reportes,
                linkTo(methodOn(ReporteController.class).getReportesByUsuario(usuarioId)).withSelfRel());
    }

    @PostMapping("/generar")
    public ResponseEntity<EntityModel<Reporte>> generarReporte(@RequestBody Reporte reporte) {
        Reporte generatedReporte = reporteService.generarReporte(reporte);
        return ResponseEntity.ok(reporteAssembler.toModel(generatedReporte));
    }

    @PostMapping("/estadisticas/ventas")
    public ResponseEntity<EntityModel<Reporte>> generarEstadisticasVentas(
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam LocalDateTime fechaFin) {
        Reporte reporte = reporteService.generarEstadisticasVentas(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporteAssembler.toModel(reporte));
    }

    @PostMapping("/estadisticas/inventario")
    public ResponseEntity<EntityModel<Reporte>> generarEstadisticasInventario() {
        Reporte reporte = reporteService.generarEstadisticasInventario();
        return ResponseEntity.ok(reporteAssembler.toModel(reporte));
    }

    @PostMapping("/estadisticas/usuarios")
    public ResponseEntity<EntityModel<Reporte>> generarEstadisticasUsuarios() {
        Reporte reporte = reporteService.generarEstadisticasUsuarios();
        return ResponseEntity.ok(reporteAssembler.toModel(reporte));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id) {
        reporteService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 