package com.proyectoSpring.fullstack.repository;

import com.proyectoSpring.fullstack.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {
    Optional<Envio> findByPedidoId(Long pedidoId);
    List<Envio> findByEstado(String estado);
    List<Envio> findByRepartidorId(Long repartidorId);
    List<Envio> findByCodigoSeguimiento(String codigoSeguimiento);
} 