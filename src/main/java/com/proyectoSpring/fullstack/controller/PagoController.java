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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
@Tag(name = "Pagos", description = "Gestión de pagos")
public class PagoController {
    private final PagoService pagoService;
    private final PagoAssembler pagoAssembler;

    @Autowired
    public PagoController(PagoService pagoService, PagoAssembler pagoAssembler) {
        this.pagoService = pagoService;
        this.pagoAssembler = pagoAssembler;
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los pagos",
        description = "Retorna una lista de todos los pagos registrados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de pagos obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Pago>> getAllPagos() {
        List<EntityModel<Pago>> pagos = pagoService.findAll().stream()
                .map(pagoAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(pagos,
                linkTo(methodOn(PagoController.class).getAllPagos()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener pago por ID",
        description = "Retorna un pago específico basado en su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Pago encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Pago no encontrado"
        )
    })
    public ResponseEntity<EntityModel<Pago>> getPagoById(
            @Parameter(description = "ID del pago", required = true) @PathVariable Long id) {
        return pagoService.findById(id)
                .map(pagoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Crear nuevo pago",
        description = "Crea un nuevo registro de pago en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Pago creado exitosamente",
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
    public ResponseEntity<EntityModel<Pago>> createPago(
            @Parameter(description = "Datos del pago a crear", required = true) @RequestBody Pago pago) {
        Pago savedPago = pagoService.save(pago);
        return ResponseEntity.ok(pagoAssembler.toModel(savedPago));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar pago",
        description = "Actualiza los datos de un pago existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Pago actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de entrada inválidos"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Pago no encontrado"
        )
    })
    public ResponseEntity<EntityModel<Pago>> updatePago(
            @Parameter(description = "ID del pago a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Datos actualizados del pago", required = true) @RequestBody Pago pago) {
        return pagoService.findById(id)
                .map(existing -> {
                    pago.setId(id);
                    Pago updatedPago = pagoService.save(pago);
                    return ResponseEntity.ok(pagoAssembler.toModel(updatedPago));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar pago",
        description = "Elimina un pago del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Pago eliminado exitosamente"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Pago no encontrado"
        )
    })
    public ResponseEntity<Void> deletePago(
            @Parameter(description = "ID del pago a eliminar", required = true) @PathVariable Long id) {
        pagoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}