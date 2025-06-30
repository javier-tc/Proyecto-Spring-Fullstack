package com.proyectoSpring.fullstack.assembler;

import com.proyectoSpring.fullstack.controller.ReporteController;
import com.proyectoSpring.fullstack.model.Reporte;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReporteAssembler implements RepresentationModelAssembler<Reporte, EntityModel<Reporte>> {

    @Override
    public EntityModel<Reporte> toModel(Reporte reporte) {
        return EntityModel.of(reporte,
                linkTo(methodOn(ReporteController.class).getReporteById(reporte.getId())).withSelfRel(),
                linkTo(methodOn(ReporteController.class).getAllReportes()).withRel("reportes"),
                linkTo(methodOn(ReporteController.class).getReportesByTipo(reporte.getTipo().name())).withRel("reportesTipo"),
                linkTo(methodOn(ReporteController.class).getReportesByUsuario(reporte.getGeneradoPor().getId())).withRel("reportesUsuario")
        );
    }
} 