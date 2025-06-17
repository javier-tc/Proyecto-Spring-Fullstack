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

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {
    private final CategoriaService categoriaService;
    private final CategoriaAssembler categoriaAssembler;

    @Autowired
    public CategoriaController(CategoriaService categoriaService, CategoriaAssembler categoriaAssembler) {
        this.categoriaService = categoriaService;
        this.categoriaAssembler = categoriaAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Categoria>> getAllCategorias() {
        List<EntityModel<Categoria>> categorias = categoriaService.findAll().stream()
                .map(categoriaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(categorias,
                linkTo(methodOn(CategoriaController.class).getAllCategorias()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Categoria>> getCategoriaById(@PathVariable Long id) {
        return categoriaService.findById(id)
                .map(categoriaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Categoria>> createCategoria(@RequestBody Categoria categoria) {
        Categoria savedCategoria = categoriaService.save(categoria);
        return ResponseEntity.ok(categoriaAssembler.toModel(savedCategoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Categoria>> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categoriaService.findById(id)
                .map(existing -> {
                    categoria.setId(id);
                    Categoria updatedCategoria = categoriaService.save(categoria);
                    return ResponseEntity.ok(categoriaAssembler.toModel(updatedCategoria));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 