package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    // Buscar pagos por pedido
    List<Pago> findByPedidoId(Long pedidoId);
}