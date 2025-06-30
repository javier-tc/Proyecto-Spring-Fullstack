package com.proyectoSpring.fullstack.assembler;

import com.proyectoSpring.fullstack.controller.LogisticaController;
import com.proyectoSpring.fullstack.model.Envio;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LogisticaAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
                linkTo(methodOn(LogisticaController.class).getEnvioById(envio.getId())).withSelfRel(),
                linkTo(methodOn(LogisticaController.class).getAllEnvios()).withRel("envios"),
                linkTo(methodOn(LogisticaController.class).getEnvioByPedido(envio.getPedido().getId())).withRel("envioPedido"),
                linkTo(methodOn(LogisticaController.class).actualizarEstadoEnvio(envio.getId(), envio.getEstado())).withRel("actualizarEstado")
        );
    }
} 