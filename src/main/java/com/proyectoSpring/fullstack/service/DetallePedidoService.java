package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.DetallePedido;
import com.proyectoSpring.fullstack.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DetallePedidoService {
    private final DetallePedidoRepository detallePedidoRepository;

    @Autowired
    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    public List<DetallePedido> findAll() {
        return detallePedidoRepository.findAll();
    }

    public Optional<DetallePedido> findById(Long id) {
        return detallePedidoRepository.findById(id);
    }

    public List<DetallePedido> findByPedidoId(Long pedidoId) {
        return detallePedidoRepository.findByPedidoId(pedidoId);
    }

    public DetallePedido save(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    public void deleteById(Long id) {
        detallePedidoRepository.deleteById(id);
    }
}