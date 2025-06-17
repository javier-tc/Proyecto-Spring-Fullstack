package com.proyectoSpring.fullstack.assembler;

import com.proyectoSpring.fullstack.controller.PagoController;
import com.proyectoSpring.fullstack.model.Pago;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PagoAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>> {

    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoController.class).getPagoById(pago.getId())).withSelfRel(),
                linkTo(methodOn(PagoController.class).getAllPagos()).withRel("pagos")
        );
    }
} 