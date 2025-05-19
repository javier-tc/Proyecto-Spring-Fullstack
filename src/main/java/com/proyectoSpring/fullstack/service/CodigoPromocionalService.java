package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.CodigoPromocional;
import com.proyectoSpring.fullstack.repository.CodigoPromocionalRepository;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodigoPromocionalService {
    private final CodigoPromocionalRepository codigoPromocionalRepository;

    @Autowired
    public CodigoPromocionalService(CodigoPromocionalRepository codigoPromocionalRepository) {
        this.codigoPromocionalRepository = codigoPromocionalRepository;
    }

    public List<CodigoPromocional> findAll() {
        return codigoPromocionalRepository.findAll();
    }

    public Optional<CodigoPromocional> findById(Long id) {
        return codigoPromocionalRepository.findById(id);
    }

    public Optional<CodigoPromocional> findByCodigo(String codigo) {
        return codigoPromocionalRepository.findByCodigo(codigo);
    }

    public CodigoPromocional save(CodigoPromocional codigoPromocional) {
        return codigoPromocionalRepository.save(codigoPromocional);
    }

    public void deleteById(Long id) {
        codigoPromocionalRepository.deleteById(id);
    }

    // Aplica un código promocional a una compra si es válido.

    @Transactional
    public double aplicarCodigo(String codigo, double montoCompra) {
        return codigoPromocionalRepository.findByCodigo(codigo)
                .filter(CodigoPromocional::esValido)
                .map(cp -> {
                    double descuento = cp.calcularDescuento(montoCompra);
                    cp.incrementarUso();
                    codigoPromocionalRepository.save(cp);
                    return descuento;
                })
                .orElse(0.0);
    }
}