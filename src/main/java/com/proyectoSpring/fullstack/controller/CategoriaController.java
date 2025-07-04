package com.proyectoSpring.fullstack.controller;

import com.proyectoSpring.fullstack.assembler.CategoriaAssembler;
import com.proyectoSpring.fullstack.model.Categoria;
import com.proyectoSpring.fullstack.service.CategoriaService;
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
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
@Tag(name = "Categorías", description = "Gestión de categorías de productos")
public class CategoriaController {
    private final CategoriaService categoriaService;
    private final CategoriaAssembler categoriaAssembler;

    @Autowired
    public CategoriaController(CategoriaService categoriaService, CategoriaAssembler categoriaAssembler) {
        this.categoriaService = categoriaService;
        this.categoriaAssembler = categoriaAssembler;
    }

    @GetMapping
    @Operation(
        summary = "Obtener todas las categorías",
        description = "Retorna una lista de todas las categorías disponibles"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de categorías obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Categoria>> getAllCategorias() {
        List<EntityModel<Categoria>> categorias = categoriaService.findAll().stream()
                .map(categoriaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(categorias,
                linkTo(methodOn(CategoriaController.class).getAllCategorias()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener categoría por ID",
        description = "Retorna una categoría específica basada en su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Categoría encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Categoría no encontrada"
        )
    })
    public ResponseEntity<EntityModel<Categoria>> getCategoriaById(
            @Parameter(description = "ID de la categoría", required = true) @PathVariable Long id) {
        return categoriaService.findById(id)
                .map(categoriaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Crear nueva categoría",
        description = "Crea una nueva categoría en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Categoría creada exitosamente",
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
    public ResponseEntity<EntityModel<Categoria>> createCategoria(
            @Parameter(description = "Datos de la categoría a crear", required = true) @RequestBody Categoria categoria) {
        Categoria savedCategoria = categoriaService.save(categoria);
        return ResponseEntity.ok(categoriaAssembler.toModel(savedCategoria));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar categoría",
        description = "Actualiza los datos de una categoría existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Categoría actualizada exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de entrada inválidos"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Categoría no encontrada"
        )
    })
    public ResponseEntity<EntityModel<Categoria>> updateCategoria(
            @Parameter(description = "ID de la categoría a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la categoría", required = true) @RequestBody Categoria categoria) {
        return categoriaService.findById(id)
                .map(existing -> {
                    categoria.setId(id);
                    Categoria updatedCategoria = categoriaService.save(categoria);
                    return ResponseEntity.ok(categoriaAssembler.toModel(updatedCategoria));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar categoría",
        description = "Elimina una categoría del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Categoría eliminada exitosamente"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Categoría no encontrada"
        )
    })
    public ResponseEntity<Void> deleteCategoria(
            @Parameter(description = "ID de la categoría a eliminar", required = true) @PathVariable Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 