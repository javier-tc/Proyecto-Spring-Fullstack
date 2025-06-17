package com.proyectoSpring.fullstack.assembler;

import com.proyectoSpring.fullstack.controller.InventarioController;
import com.proyectoSpring.fullstack.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductoAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(InventarioController.class).getInventarioDummySucursal()).withSelfRel(),
                linkTo(methodOn(InventarioController.class).getInventarioDummySucursal()).withRel("inventario")
        );
    }
} 