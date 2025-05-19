package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.Producto;
import com.proyectoSpring.fullstack.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    // Verifica el stock y desactiva el producto si está por debajo del mínimo.

    @Transactional
    public void verificarYDesactivarSiStockBajo(Long productoId) {
        productoRepository.findById(productoId).ifPresent(producto -> {
            if (producto.getStock() <= producto.getStockMinimo()) {
                producto.setActivo(false);
                productoRepository.save(producto);
            }
        });
    }
}