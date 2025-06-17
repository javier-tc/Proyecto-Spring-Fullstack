package com.proyectoSpring.fullstack.assembler;

import com.proyectoSpring.fullstack.controller.SucursalController;
import com.proyectoSpring.fullstack.model.Sucursal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SucursalAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalController.class).getSucursalById(sucursal.getId())).withSelfRel(),
                linkTo(methodOn(SucursalController.class).getAllSucursales()).withRel("sucursales")
        );
    }
} 