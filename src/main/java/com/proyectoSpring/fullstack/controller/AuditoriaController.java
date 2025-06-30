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

@RestController
@RequestMapping("/api/auditoria")
@CrossOrigin(origins = "*")
public class AuditoriaController {
    private final AuditoriaService auditoriaService;
    private final AuditoriaAssembler auditoriaAssembler;

    @Autowired
    public AuditoriaController(AuditoriaService auditoriaService, AuditoriaAssembler auditoriaAssembler) {
        this.auditoriaService = auditoriaService;
        this.auditoriaAssembler = auditoriaAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Auditoria>> getAllAuditorias() {
        List<EntityModel<Auditoria>> auditorias = auditoriaService.findAll().stream()
                .map(auditoriaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(auditorias,
                linkTo(methodOn(AuditoriaController.class).getAllAuditorias()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Auditoria>> getAuditoriaById(@PathVariable Long id) {
        return auditoriaService.findById(id)
                .map(auditoriaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public CollectionModel<EntityModel<Auditoria>> getAuditoriasByUsuario(@PathVariable Long usuarioId) {
        List<EntityModel<Auditoria>> auditorias = auditoriaService.findByUsuarioId(usuarioId).stream()
                .map(auditoriaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(auditorias,
                linkTo(methodOn(AuditoriaController.class).getAuditoriasByUsuario(usuarioId)).withSelfRel());
    }

    @GetMapping("/tipo/{tipoAccion}")
    public CollectionModel<EntityModel<Auditoria>> getAuditoriasByTipo(@PathVariable String tipoAccion) {
        List<EntityModel<Auditoria>> auditorias = auditoriaService.findByTipoAccion(tipoAccion).stream()
                .map(auditoriaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(auditorias,
                linkTo(methodOn(AuditoriaController.class).getAuditoriasByTipo(tipoAccion)).withSelfRel());
    }

    @GetMapping("/fecha")
    public CollectionModel<EntityModel<Auditoria>> getAuditoriasByFecha(
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam LocalDateTime fechaFin) {
        List<EntityModel<Auditoria>> auditorias = auditoriaService.findByFechaBetween(fechaInicio, fechaFin).stream()
                .map(auditoriaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(auditorias,
                linkTo(methodOn(AuditoriaController.class).getAuditoriasByFecha(fechaInicio, fechaFin)).withSelfRel());
    }

    @GetMapping("/entidad/{entidad}")
    public CollectionModel<EntityModel<Auditoria>> getAuditoriasByEntidad(@PathVariable String entidad) {
        List<EntityModel<Auditoria>> auditorias = auditoriaService.findByEntidad(entidad).stream()
                .map(auditoriaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(auditorias,
                linkTo(methodOn(AuditoriaController.class).getAuditoriasByEntidad(entidad)).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Auditoria>> createAuditoria(@RequestBody Auditoria auditoria) {
        Auditoria savedAuditoria = auditoriaService.save(auditoria);
        return ResponseEntity.ok(auditoriaAssembler.toModel(savedAuditoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuditoria(@PathVariable Long id) {
        auditoriaService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 