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

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionController {
    private final NotificacionService notificacionService;
    private final NotificacionAssembler notificacionAssembler;

    @Autowired
    public NotificacionController(NotificacionService notificacionService, NotificacionAssembler notificacionAssembler) {
        this.notificacionService = notificacionService;
        this.notificacionAssembler = notificacionAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Notificacion>> getAllNotificaciones() {
        List<EntityModel<Notificacion>> notificaciones = notificacionService.findAll().stream()
                .map(notificacionAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(notificaciones,
                linkTo(methodOn(NotificacionController.class).getAllNotificaciones()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Notificacion>> getNotificacionById(@PathVariable Long id) {
        return notificacionService.findById(id)
                .map(notificacionAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public CollectionModel<EntityModel<Notificacion>> getNotificacionesByUsuario(@PathVariable Long usuarioId) {
        List<EntityModel<Notificacion>> notificaciones = notificacionService.findByUsuarioId(usuarioId).stream()
                .map(notificacionAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(notificaciones,
                linkTo(methodOn(NotificacionController.class).getNotificacionesByUsuario(usuarioId)).withSelfRel());
    }

    @GetMapping("/no-leidas/{usuarioId}")
    public CollectionModel<EntityModel<Notificacion>> getNotificacionesNoLeidas(@PathVariable Long usuarioId) {
        List<EntityModel<Notificacion>> notificaciones = notificacionService.findNoLeidasByUsuarioId(usuarioId).stream()
                .map(notificacionAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(notificaciones,
                linkTo(methodOn(NotificacionController.class).getNotificacionesNoLeidas(usuarioId)).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Notificacion>> createNotificacion(@RequestBody Notificacion notificacion) {
        Notificacion savedNotificacion = notificacionService.save(notificacion);
        return ResponseEntity.ok(notificacionAssembler.toModel(savedNotificacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Notificacion>> updateNotificacion(@PathVariable Long id, @RequestBody Notificacion notificacion) {
        return notificacionService.findById(id)
                .map(existing -> {
                    notificacion.setId(id);
                    Notificacion updatedNotificacion = notificacionService.save(notificacion);
                    return ResponseEntity.ok(notificacionAssembler.toModel(updatedNotificacion));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/marcar-leida")
    public ResponseEntity<EntityModel<Notificacion>> marcarComoLeida(@PathVariable Long id) {
        return notificacionService.marcarComoLeida(id)
                .map(notificacionAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable Long id) {
        notificacionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 