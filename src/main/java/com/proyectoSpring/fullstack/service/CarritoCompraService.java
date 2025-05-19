package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.CarritoCompra;
import com.proyectoSpring.fullstack.repository.CarritoCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoCompraService {
    private final CarritoCompraRepository carritoCompraRepository;

    @Autowired
    public CarritoCompraService(CarritoCompraRepository carritoCompraRepository) {
        this.carritoCompraRepository = carritoCompraRepository;
    }

    public List<CarritoCompra> findAll() {
        return carritoCompraRepository.findAll();
    }

    public Optional<CarritoCompra> findById(Long id) {
        return carritoCompraRepository.findById(id);
    }

    public Optional<CarritoCompra> findByUsuarioId(Long usuarioId) {
        return carritoCompraRepository.findByUsuarioId(usuarioId);
    }

    public CarritoCompra save(CarritoCompra carritoCompra) {
        return carritoCompraRepository.save(carritoCompra);
    }

    public void deleteById(Long id) {
        carritoCompraRepository.deleteById(id);
    }
}