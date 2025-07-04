package com.proyectoSpring.fullstack.controller;

import com.proyectoSpring.fullstack.assembler.AuditoriaAssembler;
import com.proyectoSpring.fullstack.model.Auditoria;
import com.proyectoSpring.fullstack.service.AuditoriaService;
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
@RequestMapping("/api/auditoria")
@CrossOrigin(origins = "*")
@Tag(name = "Auditoría", description = "Gestión de registros de auditoría del sistema")
public class AuditoriaController {
    private final AuditoriaService auditoriaService;
    private final AuditoriaAssembler auditoriaAssembler;

    @Autowired
    public AuditoriaController(AuditoriaService auditoriaService, AuditoriaAssembler auditoriaAssembler) {
        this.auditoriaService = auditoriaService;
        this.auditoriaAssembler = auditoriaAssembler;
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los registros de auditoría",
        description = "Retorna una lista de todos los registros de auditoría del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de registros de auditoría obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Auditoria>> getAllAuditorias() {
        List<EntityModel<Auditoria>> auditorias = auditoriaService.findAll().stream()
                .map(auditoriaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(auditorias,
                linkTo(methodOn(AuditoriaController.class).getAllAuditorias()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener registro de auditoría por ID",
        description = "Retorna un registro de auditoría específico basado en su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Registro de auditoría encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Registro de auditoría no encontrado"
        )
    })
    public ResponseEntity<EntityModel<Auditoria>> getAuditoriaById(
            @Parameter(description = "ID del registro de auditoría", required = true) @PathVariable Long id) {
        return auditoriaService.findById(id)
                .map(auditoriaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(
        summary = "Obtener registros de auditoría por usuario",
        description = "Retorna una lista de registros de auditoría de un usuario específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de registros de auditoría obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Auditoria>> getAuditoriasByUsuario(
            @Parameter(description = "ID del usuario", required = true) @PathVariable Long usuarioId) {
        List<EntityModel<Auditoria>> auditorias = auditoriaService.findByUsuarioId(usuarioId).stream()
                .map(auditoriaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(auditorias,
                linkTo(methodOn(AuditoriaController.class).getAuditoriasByUsuario(usuarioId)).withSelfRel());
    }

    @GetMapping("/tipo/{tipoAccion}")
    @Operation(
        summary = "Obtener registros de auditoría por tipo de acción",
        description = "Retorna una lista de registros de auditoría filtrados por tipo de acción"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de registros de auditoría obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Auditoria>> getAuditoriasByTipo(
            @Parameter(description = "Tipo de acción", required = true) @PathVariable String tipoAccion) {
        List<EntityModel<Auditoria>> auditorias = auditoriaService.findByTipoAccion(tipoAccion).stream()
                .map(auditoriaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(auditorias,
                linkTo(methodOn(AuditoriaController.class).getAuditoriasByTipo(tipoAccion)).withSelfRel());
    }

    @GetMapping("/fecha")
    @Operation(
        summary = "Obtener registros de auditoría por rango de fechas",
        description = "Retorna una lista de registros de auditoría dentro de un rango de fechas específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de registros de auditoría obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
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
    public CollectionModel<EntityModel<Auditoria>> getAuditoriasByFecha(
            @Parameter(description = "Fecha de inicio del rango", required = true) @RequestParam LocalDateTime fechaInicio,
            @Parameter(description = "Fecha de fin del rango", required = true) @RequestParam LocalDateTime fechaFin) {
        List<EntityModel<Auditoria>> auditorias = auditoriaService.findByFechaBetween(fechaInicio, fechaFin).stream()
                .map(auditoriaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(auditorias,
                linkTo(methodOn(AuditoriaController.class).getAuditoriasByFecha(fechaInicio, fechaFin)).withSelfRel());
    }

    @GetMapping("/entidad/{entidad}")
    @Operation(
        summary = "Obtener registros de auditoría por entidad",
        description = "Retorna una lista de registros de auditoría filtrados por entidad afectada"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de registros de auditoría obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Auditoria>> getAuditoriasByEntidad(
            @Parameter(description = "Nombre de la entidad", required = true) @PathVariable String entidad) {
        List<EntityModel<Auditoria>> auditorias = auditoriaService.findByEntidad(entidad).stream()
                .map(auditoriaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(auditorias,
                linkTo(methodOn(AuditoriaController.class).getAuditoriasByEntidad(entidad)).withSelfRel());
    }

    @PostMapping
    @Operation(
        summary = "Crear nuevo registro de auditoría",
        description = "Crea un nuevo registro de auditoría en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Registro de auditoría creado exitosamente",
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
    public ResponseEntity<EntityModel<Auditoria>> createAuditoria(
            @Parameter(description = "Datos del registro de auditoría a crear", required = true) @RequestBody Auditoria auditoria) {
        Auditoria savedAuditoria = auditoriaService.save(auditoria);
        return ResponseEntity.ok(auditoriaAssembler.toModel(savedAuditoria));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar registro de auditoría",
        description = "Elimina un registro de auditoría del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Registro de auditoría eliminado exitosamente"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Registro de auditoría no encontrado"
        )
    })
    public ResponseEntity<Void> deleteAuditoria(
            @Parameter(description = "ID del registro de auditoría a eliminar", required = true) @PathVariable Long id) {
        auditoriaService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 