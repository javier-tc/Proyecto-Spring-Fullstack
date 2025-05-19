package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.Pago;
import com.proyectoSpring.fullstack.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;

    @Autowired
    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> findById(Long id) {
        return pagoRepository.findById(id);
    }

    public List<Pago> findByPedidoId(Long pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId);
    }

    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    public void deleteById(Long id) {
        pagoRepository.deleteById(id);
    }
}