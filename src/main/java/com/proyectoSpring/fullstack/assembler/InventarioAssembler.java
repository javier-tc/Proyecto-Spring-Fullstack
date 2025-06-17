package com.proyectoSpring.fullstack.assembler;

import com.proyectoSpring.fullstack.controller.InventarioController;
import com.proyectoSpring.fullstack.model.Inventario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InventarioAssembler implements RepresentationModelAssembler<Inventario, EntityModel<Inventario>> {

    @Override
    public EntityModel<Inventario> toModel(Inventario inventario) {
        return EntityModel.of(inventario,
                linkTo(methodOn(InventarioController.class).getInventarioDummySucursal()).withSelfRel(),
                linkTo(methodOn(InventarioController.class).getInventarioDummySucursal()).withRel("inventarioSucursal")
        );
    }
} 