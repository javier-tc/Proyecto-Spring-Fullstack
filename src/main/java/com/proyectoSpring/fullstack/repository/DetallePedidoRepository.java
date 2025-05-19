package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    // Buscar detalles por pedido
    List<DetallePedido> findByPedidoId(Long pedidoId);
}