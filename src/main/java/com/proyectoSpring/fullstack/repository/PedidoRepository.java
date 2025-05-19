package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Buscar pedidos por usuario
    List<Pedido> findByUsuarioId(Long usuarioId);
}