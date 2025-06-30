package com.proyectoSpring.fullstack.assembler;

import com.proyectoSpring.fullstack.controller.AuditoriaController;
import com.proyectoSpring.fullstack.model.Auditoria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuditoriaAssembler implements RepresentationModelAssembler<Auditoria, EntityModel<Auditoria>> {

    @Override
    public EntityModel<Auditoria> toModel(Auditoria auditoria) {
        return EntityModel.of(auditoria,
                linkTo(methodOn(AuditoriaController.class).getAuditoriaById(auditoria.getId())).withSelfRel(),
                linkTo(methodOn(AuditoriaController.class).getAllAuditorias()).withRel("auditorias"),
                linkTo(methodOn(AuditoriaController.class).getAuditoriasByUsuario(auditoria.getUsuario().getId())).withRel("auditoriasUsuario"),
                linkTo(methodOn(AuditoriaController.class).getAuditoriasByTipo(auditoria.getTipoAccion().name())).withRel("auditoriasTipo"),
                linkTo(methodOn(AuditoriaController.class).getAuditoriasByEntidad(auditoria.getEntidadAfectada())).withRel("auditoriasEntidad")
        );
    }
} 