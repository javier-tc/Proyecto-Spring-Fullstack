package com.proyectoSpring.fullstack.controller;

import com.proyectoSpring.fullstack.assembler.LogisticaAssembler;
import com.proyectoSpring.fullstack.model.Envio;
import com.proyectoSpring.fullstack.service.LogisticaService;
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
@RequestMapping("/api/logistica")
@CrossOrigin(origins = "*")
public class LogisticaController {
    private final LogisticaService logisticaService;
    private final LogisticaAssembler logisticaAssembler;

    @Autowired
    public LogisticaController(LogisticaService logisticaService, LogisticaAssembler logisticaAssembler) {
        this.logisticaService = logisticaService;
        this.logisticaAssembler = logisticaAssembler;
    }

    @GetMapping("/envios")
    public CollectionModel<EntityModel<Envio>> getAllEnvios() {
        List<EntityModel<Envio>> envios = logisticaService.findAllEnvios().stream()
                .map(logisticaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(envios,
                linkTo(methodOn(LogisticaController.class).getAllEnvios()).withSelfRel());
    }

    @GetMapping("/envios/{id}")
    public ResponseEntity<EntityModel<Envio>> getEnvioById(@PathVariable Long id) {
        return logisticaService.findEnvioById(id)
                .map(logisticaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/envios/pedido/{pedidoId}")
    public ResponseEntity<EntityModel<Envio>> getEnvioByPedido(@PathVariable Long pedidoId) {
        return logisticaService.findEnvioByPedidoId(pedidoId)
                .map(logisticaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/envios/estado/{estado}")
    public CollectionModel<EntityModel<Envio>> getEnviosByEstado(@PathVariable String estado) {
        List<EntityModel<Envio>> envios = logisticaService.findEnviosByEstado(estado).stream()
                .map(logisticaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(envios,
                linkTo(methodOn(LogisticaController.class).getEnviosByEstado(estado)).withSelfRel());
    }

    @PostMapping("/envios")
    public ResponseEntity<EntityModel<Envio>> createEnvio(@RequestBody Envio envio) {
        Envio savedEnvio = logisticaService.saveEnvio(envio);
        return ResponseEntity.ok(logisticaAssembler.toModel(savedEnvio));
    }

    @PutMapping("/envios/{id}")
    public ResponseEntity<EntityModel<Envio>> updateEnvio(@PathVariable Long id, @RequestBody Envio envio) {
        return logisticaService.findEnvioById(id)
                .map(existing -> {
                    envio.setId(id);
                    Envio updatedEnvio = logisticaService.saveEnvio(envio);
                    return ResponseEntity.ok(logisticaAssembler.toModel(updatedEnvio));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/envios/{id}/estado")
    public ResponseEntity<EntityModel<Envio>> actualizarEstadoEnvio(@PathVariable Long id, @RequestParam String estado) {
        return logisticaService.actualizarEstadoEnvio(id, estado)
                .map(logisticaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/envios/{id}")
    public ResponseEntity<Void> deleteEnvio(@PathVariable Long id) {
        logisticaService.deleteEnvioById(id);
        return ResponseEntity.ok().build();
    }
} 