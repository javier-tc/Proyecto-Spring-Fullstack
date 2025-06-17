package com.proyectoSpring.fullstack.controller;

import com.proyectoSpring.fullstack.assembler.InventarioAssembler;
import com.proyectoSpring.fullstack.dto.InventarioRequest;
import com.proyectoSpring.fullstack.model.Inventario;
import com.proyectoSpring.fullstack.model.Producto;
import com.proyectoSpring.fullstack.model.Sucursal;
import com.proyectoSpring.fullstack.service.InventarioService;
import com.proyectoSpring.fullstack.service.ProductoService;
import com.proyectoSpring.fullstack.service.CategoriaService;
import com.proyectoSpring.fullstack.model.Categoria;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    private static final Logger logger = LoggerFactory.getLogger(InventarioController.class);
    private final InventarioService inventarioService;
    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final InventarioAssembler inventarioAssembler;

    @Autowired
    public InventarioController(InventarioService inventarioService,
                                ProductoService productoService,
                                CategoriaService categoriaService,
                                InventarioAssembler inventarioAssembler) {
        this.inventarioService = inventarioService;
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.inventarioAssembler = inventarioAssembler;
    }

    private final Long dummySucursalId = 1L;

    @GetMapping("/sucursal")
    public CollectionModel<EntityModel<Inventario>> getInventarioDummySucursal() {
        List<EntityModel<Inventario>> inventarios = inventarioService.findBySucursalId(dummySucursalId).stream()
                .map(inventarioAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(inventarios,
                linkTo(methodOn(InventarioController.class).getInventarioDummySucursal()).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Inventario>> crearProductoEnInventario(@Valid @RequestBody InventarioRequest request) {
        try {
            // Crear o actualizar el producto
            Producto producto = productoService.findByCodigo(request.getCodigo())
                    .orElse(new Producto());

            producto.setCodigo(request.getCodigo());
            producto.setNombre(request.getNombre());
            producto.setDescripcion(request.getDescripcion());
            producto.setPrecio(request.getPrecio());
            producto.setStock(request.getCantidad());
            producto.setStockMinimo(request.getStockMinimo());
            producto.setActivo(true);
             // Suponiendo que tienes un servicio de categoría:
             Categoria categoria = categoriaService.findById(request.getCategoriaId())
             .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(categoria);
            
            producto = productoService.save(producto);

            // Crear o actualizar el inventario
            Inventario inventario = new Inventario();
            inventario.setProducto(producto);
            // inventario.setSucursal(sucursalService.findById(dummySucursalId)
            
            Sucursal dummySucursal = new Sucursal();
            dummySucursal.setId(1L);
            inventario.setSucursal(dummySucursal);
            inventario.setCantidad(request.getCantidad());
            inventario.setStockMinimo(request.getStockMinimo());
            inventario.setStockMaximo(request.getStockMaximo());
            
           
            inventario = inventarioService.save(inventario);

            return ResponseEntity.ok(inventarioAssembler.toModel(inventario));
        } catch (Exception e) {
            logger.error("Error al crear producto en inventario: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{inventarioId}")
    public ResponseEntity<EntityModel<Inventario>> actualizarInventario(
            @PathVariable Long inventarioId,
            @Valid @RequestBody InventarioRequest request) {
        try {
            Inventario inventario = inventarioService.findById(inventarioId)
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

            // Actualizar el producto
            Producto producto = inventario.getProducto();
            producto.setNombre(request.getNombre());
            producto.setDescripcion(request.getDescripcion());
            producto.setPrecio(request.getPrecio());
            producto.setStockMinimo(request.getStockMinimo());
            producto = productoService.save(producto);

            // Actualizar el inventario
            inventario.setCantidad(request.getCantidad());
            inventario.setStockMinimo(request.getStockMinimo());
            inventario.setStockMaximo(request.getStockMaximo());

            inventario = inventarioService.save(inventario);

            return ResponseEntity.ok(inventarioAssembler.toModel(inventario));
        } catch (Exception e) {
            logger.error("Error al actualizar inventario: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{inventarioId}")
    public ResponseEntity<Void> eliminarInventario(@PathVariable Long inventarioId) {
        try {
            Inventario inventario = inventarioService.findById(inventarioId)
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

            // Desactivar el producto en lugar de eliminarlo
            Producto producto = inventario.getProducto();
            producto.setActivo(false);
            productoService.save(producto);

            // Eliminar el inventario
            inventarioService.deleteById(inventarioId);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error al eliminar inventario: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
