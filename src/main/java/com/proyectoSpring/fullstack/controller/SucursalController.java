package com.proyectoSpring.fullstack.controller;

import com.proyectoSpring.fullstack.assembler.SucursalAssembler;
import com.proyectoSpring.fullstack.model.Sucursal;
import com.proyectoSpring.fullstack.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/sucursales")
@CrossOrigin(origins = "*")
@Tag(name = "Sucursales", description = "Gestión de sucursales")
public class SucursalController {
    private final SucursalService sucursalService;
    private final SucursalAssembler sucursalAssembler;

    @Autowired
    public SucursalController(SucursalService sucursalService, SucursalAssembler sucursalAssembler) {
        this.sucursalService = sucursalService;
        this.sucursalAssembler = sucursalAssembler;
    }

    @GetMapping
    @Operation(
        summary = "Obtener todas las sucursales",
        description = "Retorna una lista de todas las sucursales disponibles"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de sucursales obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Sucursal>> getAllSucursales() {
        List<EntityModel<Sucursal>> sucursales = sucursalService.findAll().stream()
                .map(sucursalAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(sucursales,
                linkTo(methodOn(SucursalController.class).getAllSucursales()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener sucursal por ID",
        description = "Retorna una sucursal específica basada en su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Sucursal encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Sucursal no encontrada"
        )
    })
    public ResponseEntity<EntityModel<Sucursal>> getSucursalById(
            @Parameter(description = "ID de la sucursal", required = true) @PathVariable Long id) {
        return sucursalService.findById(id)
                .map(sucursalAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Crear nueva sucursal",
        description = "Crea una nueva sucursal en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Sucursal creada exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de entrada inválidos"
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<EntityModel<Sucursal>> createSucursal(
            @Parameter(description = "Datos de la sucursal a crear", required = true) @RequestBody Sucursal sucursal) {
        Sucursal savedSucursal = sucursalService.save(sucursal);
        return ResponseEntity.ok(sucursalAssembler.toModel(savedSucursal));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar sucursal",
        description = "Actualiza los datos de una sucursal existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Sucursal actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de entrada inválidos"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Sucursal no encontrada"
        )
    })
    public ResponseEntity<EntityModel<Sucursal>> updateSucursal(
            @Parameter(description = "ID de la sucursal a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la sucursal", required = true) @RequestBody Sucursal sucursal) {
        return sucursalService.findById(id)
                .map(existing -> {
                    sucursal.setId(id);
                    Sucursal updatedSucursal = sucursalService.save(sucursal);
                    return ResponseEntity.ok(sucursalAssembler.toModel(updatedSucursal));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar sucursal",
        description = "Elimina una sucursal del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Sucursal eliminada exitosamente"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Sucursal no encontrada"
        )
    })
    public ResponseEntity<Void> deleteSucursal(
            @Parameter(description = "ID de la sucursal a eliminar", required = true) @PathVariable Long id) {
        sucursalService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 