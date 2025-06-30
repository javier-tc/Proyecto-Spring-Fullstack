package com.proyectoSpring.fullstack.assembler;

import com.proyectoSpring.fullstack.controller.NotificacionController;
import com.proyectoSpring.fullstack.model.Notificacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NotificacionAssembler implements RepresentationModelAssembler<Notificacion, EntityModel<Notificacion>> {

    @Override
    public EntityModel<Notificacion> toModel(Notificacion notificacion) {
        return EntityModel.of(notificacion,
                linkTo(methodOn(NotificacionController.class).getNotificacionById(notificacion.getId())).withSelfRel(),
                linkTo(methodOn(NotificacionController.class).getAllNotificaciones()).withRel("notificaciones"),
                linkTo(methodOn(NotificacionController.class).getNotificacionesByUsuario(notificacion.getUsuario().getId())).withRel("notificacionesUsuario"),
                linkTo(methodOn(NotificacionController.class).marcarComoLeida(notificacion.getId())).withRel("marcarLeida")
        );
    }
} 