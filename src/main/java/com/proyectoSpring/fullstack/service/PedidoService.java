package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.Pedido;
import com.proyectoSpring.fullstack.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    @Transactional
    public Pedido cambiarEstado(Long pedidoId, Pedido.EstadoPedido nuevoEstado) {
        return pedidoRepository.findById(pedidoId)
                .map(pedido -> {
                    pedido.setEstado(nuevoEstado);
                    pedido.setFechaActualizacion(java.time.LocalDateTime.now());
                    return pedidoRepository.save(pedido);
                })
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }
}