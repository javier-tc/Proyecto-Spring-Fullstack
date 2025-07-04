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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
@Tag(name = "Reportes", description = "Generación y gestión de reportes")
public class ReporteController {
    private final ReporteService reporteService;
    private final ReporteAssembler reporteAssembler;

    @Autowired
    public ReporteController(ReporteService reporteService, ReporteAssembler reporteAssembler) {
        this.reporteService = reporteService;
        this.reporteAssembler = reporteAssembler;
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los reportes",
        description = "Retorna una lista de todos los reportes generados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de reportes obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Reporte>> getAllReportes() {
        List<EntityModel<Reporte>> reportes = reporteService.findAll().stream()
                .map(reporteAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(reportes,
                linkTo(methodOn(ReporteController.class).getAllReportes()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener reporte por ID",
        description = "Retorna un reporte específico basado en su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Reporte encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Reporte no encontrado"
        )
    })
    public ResponseEntity<EntityModel<Reporte>> getReporteById(
            @Parameter(description = "ID del reporte", required = true) @PathVariable Long id) {
        return reporteService.findById(id)
                .map(reporteAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(
        summary = "Obtener reportes por tipo",
        description = "Retorna una lista de reportes filtrados por su tipo"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de reportes obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Reporte>> getReportesByTipo(
            @Parameter(description = "Tipo de reporte", required = true) @PathVariable String tipo) {
        List<EntityModel<Reporte>> reportes = reporteService.findByTipo(tipo).stream()
                .map(reporteAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(reportes,
                linkTo(methodOn(ReporteController.class).getReportesByTipo(tipo)).withSelfRel());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(
        summary = "Obtener reportes por usuario",
        description = "Retorna una lista de reportes generados por un usuario específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de reportes obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Reporte>> getReportesByUsuario(
            @Parameter(description = "ID del usuario", required = true) @PathVariable Long usuarioId) {
        List<EntityModel<Reporte>> reportes = reporteService.findByUsuarioId(usuarioId).stream()
                .map(reporteAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(reportes,
                linkTo(methodOn(ReporteController.class).getReportesByUsuario(usuarioId)).withSelfRel());
    }

    @PostMapping("/generar")
    @Operation(
        summary = "Generar reporte personalizado",
        description = "Genera un nuevo reporte personalizado en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Reporte generado exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de entrada inválidos"
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<EntityModel<Reporte>> generarReporte(
            @Parameter(description = "Datos del reporte a generar", required = true) @RequestBody Reporte reporte) {
        Reporte generatedReporte = reporteService.generarReporte(reporte);
        return ResponseEntity.ok(reporteAssembler.toModel(generatedReporte));
    }

    @PostMapping("/estadisticas/ventas")
    @Operation(
        summary = "Generar estadísticas de ventas",
        description = "Genera un reporte de estadísticas de ventas para un período específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Estadísticas de ventas generadas exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Fechas inválidas"
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<EntityModel<Reporte>> generarEstadisticasVentas(
            @Parameter(description = "Fecha de inicio del período", required = true) @RequestParam LocalDateTime fechaInicio,
            @Parameter(description = "Fecha de fin del período", required = true) @RequestParam LocalDateTime fechaFin) {
        Reporte reporte = reporteService.generarEstadisticasVentas(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporteAssembler.toModel(reporte));
    }

    @PostMapping("/estadisticas/inventario")
    @Operation(
        summary = "Generar estadísticas de inventario",
        description = "Genera un reporte de estadísticas del inventario actual"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Estadísticas de inventario generadas exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<EntityModel<Reporte>> generarEstadisticasInventario() {
        Reporte reporte = reporteService.generarEstadisticasInventario();
        return ResponseEntity.ok(reporteAssembler.toModel(reporte));
    }

    @PostMapping("/estadisticas/usuarios")
    @Operation(
        summary = "Generar estadísticas de usuarios",
        description = "Genera un reporte de estadísticas de usuarios del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Estadísticas de usuarios generadas exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<EntityModel<Reporte>> generarEstadisticasUsuarios() {
        Reporte reporte = reporteService.generarEstadisticasUsuarios();
        return ResponseEntity.ok(reporteAssembler.toModel(reporte));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar reporte",
        description = "Elimina un reporte del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Reporte eliminado exitosamente"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Reporte no encontrado"
        )
    })
    public ResponseEntity<Void> deleteReporte(
            @Parameter(description = "ID del reporte a eliminar", required = true) @PathVariable Long id) {
        reporteService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 