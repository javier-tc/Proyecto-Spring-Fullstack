package com.proyectoSpring.fullstack.assembler;

import com.proyectoSpring.fullstack.controller.CategoriaController;
import com.proyectoSpring.fullstack.model.Categoria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoriaAssembler implements RepresentationModelAssembler<Categoria, EntityModel<Categoria>> {

    @Override
    public EntityModel<Categoria> toModel(Categoria categoria) {
        return EntityModel.of(categoria,
                linkTo(methodOn(CategoriaController.class).getCategoriaById(categoria.getId())).withSelfRel(),
                linkTo(methodOn(CategoriaController.class).getAllCategorias()).withRel("categorias")
        );
    }
} 