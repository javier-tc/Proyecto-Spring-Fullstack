package com.proyectoSpring.fullstack.controller;

import com.proyectoSpring.fullstack.assembler.PagoAssembler;
import com.proyectoSpring.fullstack.model.Pago;
import com.proyectoSpring.fullstack.service.PagoService;
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
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {
    private final PagoService pagoService;
    private final PagoAssembler pagoAssembler;

    @Autowired
    public PagoController(PagoService pagoService, PagoAssembler pagoAssembler) {
        this.pagoService = pagoService;
        this.pagoAssembler = pagoAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Pago>> getAllPagos() {
        List<EntityModel<Pago>> pagos = pagoService.findAll().stream()
                .map(pagoAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(pagos,
                linkTo(methodOn(PagoController.class).getAllPagos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pago>> getPagoById(@PathVariable Long id) {
        return pagoService.findById(id)
                .map(pagoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Pago>> createPago(@RequestBody Pago pago) {
        Pago savedPago = pagoService.save(pago);
        return ResponseEntity.ok(pagoAssembler.toModel(savedPago));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Pago>> updatePago(@PathVariable Long id, @RequestBody Pago pago) {
        return pagoService.findById(id)
                .map(existing -> {
                    pago.setId(id);
                    Pago updatedPago = pagoService.save(pago);
                    return ResponseEntity.ok(pagoAssembler.toModel(updatedPago));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        pagoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}