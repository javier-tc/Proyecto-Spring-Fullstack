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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/logistica")
@CrossOrigin(origins = "*")
@Tag(name = "Logística", description = "Gestión de envíos y logística")
public class LogisticaController {
    private final LogisticaService logisticaService;
    private final LogisticaAssembler logisticaAssembler;

    @Autowired
    public LogisticaController(LogisticaService logisticaService, LogisticaAssembler logisticaAssembler) {
        this.logisticaService = logisticaService;
        this.logisticaAssembler = logisticaAssembler;
    }

    @GetMapping("/envios")
    @Operation(
        summary = "Obtener todos los envíos",
        description = "Retorna una lista de todos los envíos registrados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de envíos obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Envio>> getAllEnvios() {
        List<EntityModel<Envio>> envios = logisticaService.findAllEnvios().stream()
                .map(logisticaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(envios,
                linkTo(methodOn(LogisticaController.class).getAllEnvios()).withSelfRel());
    }

    @GetMapping("/envios/{id}")
    @Operation(
        summary = "Obtener envío por ID",
        description = "Retorna un envío específico basado en su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Envío encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Envío no encontrado"
        )
    })
    public ResponseEntity<EntityModel<Envio>> getEnvioById(
            @Parameter(description = "ID del envío", required = true) @PathVariable Long id) {
        return logisticaService.findEnvioById(id)
                .map(logisticaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/envios/pedido/{pedidoId}")
    @Operation(
        summary = "Obtener envío por pedido",
        description = "Retorna el envío asociado a un pedido específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Envío encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Envío no encontrado para el pedido"
        )
    })
    public ResponseEntity<EntityModel<Envio>> getEnvioByPedido(
            @Parameter(description = "ID del pedido", required = true) @PathVariable Long pedidoId) {
        return logisticaService.findEnvioByPedidoId(pedidoId)
                .map(logisticaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/envios/estado/{estado}")
    @Operation(
        summary = "Obtener envíos por estado",
        description = "Retorna una lista de envíos filtrados por su estado"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de envíos obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public CollectionModel<EntityModel<Envio>> getEnviosByEstado(
            @Parameter(description = "Estado del envío", required = true) @PathVariable String estado) {
        List<EntityModel<Envio>> envios = logisticaService.findEnviosByEstado(estado).stream()
                .map(logisticaAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(envios,
                linkTo(methodOn(LogisticaController.class).getEnviosByEstado(estado)).withSelfRel());
    }

    @PostMapping("/envios")
    @Operation(
        summary = "Crear nuevo envío",
        description = "Crea un nuevo envío en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Envío creado exitosamente",
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
    public ResponseEntity<EntityModel<Envio>> createEnvio(
            @Parameter(description = "Datos del envío a crear", required = true) @RequestBody Envio envio) {
        Envio savedEnvio = logisticaService.saveEnvio(envio);
        return ResponseEntity.ok(logisticaAssembler.toModel(savedEnvio));
    }

    @PutMapping("/envios/{id}")
    @Operation(
        summary = "Actualizar envío",
        description = "Actualiza los datos de un envío existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Envío actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de entrada inválidos"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Envío no encontrado"
        )
    })
    public ResponseEntity<EntityModel<Envio>> updateEnvio(
            @Parameter(description = "ID del envío a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Datos actualizados del envío", required = true) @RequestBody Envio envio) {
        return logisticaService.findEnvioById(id)
                .map(existing -> {
                    envio.setId(id);
                    Envio updatedEnvio = logisticaService.saveEnvio(envio);
                    return ResponseEntity.ok(logisticaAssembler.toModel(updatedEnvio));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/envios/{id}/estado")
    @Operation(
        summary = "Actualizar estado de envío",
        description = "Actualiza el estado de un envío específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Estado de envío actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = EntityModel.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Estado inválido"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Envío no encontrado"
        )
    })
    public ResponseEntity<EntityModel<Envio>> actualizarEstadoEnvio(
            @Parameter(description = "ID del envío", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevo estado del envío", required = true) @RequestParam String estado) {
        return logisticaService.actualizarEstadoEnvio(id, estado)
                .map(logisticaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/envios/{id}")
    @Operation(
        summary = "Eliminar envío",
        description = "Elimina un envío del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Envío eliminado exitosamente"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Envío no encontrado"
        )
    })
    public ResponseEntity<Void> deleteEnvio(
            @Parameter(description = "ID del envío a eliminar", required = true) @PathVariable Long id) {
        logisticaService.deleteEnvioById(id);
        return ResponseEntity.ok().build();
    }
} 