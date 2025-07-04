package com.proyectoSpring.fullstack.controller;

import com.proyectoSpring.fullstack.assembler.NotificacionAssembler;
import com.proyectoSpring.fullstack.model.Notificacion;
import com.proyectoSpring.fullstack.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
@Tag(name = "Notificaciones", description = "Gestión de notificaciones del sistema")
public class NotificacionController {
    private final NotificacionService notificacionService;
    private final NotificacionAssembler notificacionAssembler;

    @Autowired
    public NotificacionController(NotificacionService notificacionService, NotificacionAssembler notificacionAssembler) {
        this.notificacionService = notificacionService;
        this.notificacionAssembler = notificacionAssembler;
    }

    @GetMapping
    @Operation(
        summary = "Obtener todas las notificaciones",
        description = "Retorna una lista de todas las notificaciones del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de notificaciones obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Notificacion>> getAllNotificaciones() {
        List<EntityModel<Notificacion>> notificaciones = notificacionService.findAll().stream()
                .map(notificacionAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(notificaciones,
                linkTo(methodOn(NotificacionController.class).getAllNotificaciones()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener notificación por ID",
        description = "Retorna una notificación específica basada en su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Notificación encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Notificación no encontrada"
        )
    })
    public ResponseEntity<EntityModel<Notificacion>> getNotificacionById(
            @Parameter(description = "ID de la notificación", required = true) @PathVariable Long id) {
        return notificacionService.findById(id)
                .map(notificacionAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(
        summary = "Obtener notificaciones por usuario",
        description = "Retorna una lista de notificaciones de un usuario específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de notificaciones obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Notificacion>> getNotificacionesByUsuario(
            @Parameter(description = "ID del usuario", required = true) @PathVariable Long usuarioId) {
        List<EntityModel<Notificacion>> notificaciones = notificacionService.findByUsuarioId(usuarioId).stream()
                .map(notificacionAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(notificaciones,
                linkTo(methodOn(NotificacionController.class).getNotificacionesByUsuario(usuarioId)).withSelfRel());
    }

    @GetMapping("/no-leidas/{usuarioId}")
    @Operation(
        summary = "Obtener notificaciones no leídas",
        description = "Retorna una lista de notificaciones no leídas de un usuario específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de notificaciones no leídas obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Notificacion>> getNotificacionesNoLeidas(
            @Parameter(description = "ID del usuario", required = true) @PathVariable Long usuarioId) {
        List<EntityModel<Notificacion>> notificaciones = notificacionService.findNoLeidasByUsuarioId(usuarioId).stream()
                .map(notificacionAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(notificaciones,
                linkTo(methodOn(NotificacionController.class).getNotificacionesNoLeidas(usuarioId)).withSelfRel());
    }

    @PostMapping
    @Operation(
        summary = "Crear nueva notificación",
        description = "Crea una nueva notificación en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Notificación creada exitosamente",
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
    public ResponseEntity<EntityModel<Notificacion>> createNotificacion(
            @Parameter(description = "Datos de la notificación a crear", required = true) @RequestBody Notificacion notificacion) {
        Notificacion savedNotificacion = notificacionService.save(notificacion);
        return ResponseEntity.ok(notificacionAssembler.toModel(savedNotificacion));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar notificación",
        description = "Actualiza los datos de una notificación existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Notificación actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de entrada inválidos"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Notificación no encontrada"
        )
    })
    public ResponseEntity<EntityModel<Notificacion>> updateNotificacion(
            @Parameter(description = "ID de la notificación a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la notificación", required = true) @RequestBody Notificacion notificacion) {
        return notificacionService.findById(id)
                .map(existing -> {
                    notificacion.setId(id);
                    Notificacion updatedNotificacion = notificacionService.save(notificacion);
                    return ResponseEntity.ok(notificacionAssembler.toModel(updatedNotificacion));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/marcar-leida")
    @Operation(
        summary = "Marcar notificación como leída",
        description = "Marca una notificación específica como leída"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Notificación marcada como leída exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Notificación no encontrada"
        )
    })
    public ResponseEntity<EntityModel<Notificacion>> marcarComoLeida(
            @Parameter(description = "ID de la notificación", required = true) @PathVariable Long id) {
        return notificacionService.marcarComoLeida(id)
                .map(notificacionAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar notificación",
        description = "Elimina una notificación del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Notificación eliminada exitosamente"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Notificación no encontrada"
        )
    })
    public ResponseEntity<Void> deleteNotificacion(
            @Parameter(description = "ID de la notificación a eliminar", required = true) @PathVariable Long id) {
        notificacionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 