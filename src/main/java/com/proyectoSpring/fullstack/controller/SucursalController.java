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

@RestController
@RequestMapping("/api/sucursales")
@CrossOrigin(origins = "*")
public class SucursalController {
    private final SucursalService sucursalService;
    private final SucursalAssembler sucursalAssembler;

    @Autowired
    public SucursalController(SucursalService sucursalService, SucursalAssembler sucursalAssembler) {
        this.sucursalService = sucursalService;
        this.sucursalAssembler = sucursalAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Sucursal>> getAllSucursales() {
        List<EntityModel<Sucursal>> sucursales = sucursalService.findAll().stream()
                .map(sucursalAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(sucursales,
                linkTo(methodOn(SucursalController.class).getAllSucursales()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Sucursal>> getSucursalById(@PathVariable Long id) {
        return sucursalService.findById(id)
                .map(sucursalAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Sucursal>> createSucursal(@RequestBody Sucursal sucursal) {
        Sucursal savedSucursal = sucursalService.save(sucursal);
        return ResponseEntity.ok(sucursalAssembler.toModel(savedSucursal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Sucursal>> updateSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        return sucursalService.findById(id)
                .map(existing -> {
                    sucursal.setId(id);
                    Sucursal updatedSucursal = sucursalService.save(sucursal);
                    return ResponseEntity.ok(sucursalAssembler.toModel(updatedSucursal));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSucursal(@PathVariable Long id) {
        sucursalService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 