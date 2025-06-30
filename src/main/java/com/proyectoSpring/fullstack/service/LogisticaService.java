package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.Envio;
import com.proyectoSpring.fullstack.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LogisticaService {
    private final EnvioRepository envioRepository;

    @Autowired
    public LogisticaService(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    public List<Envio> findAllEnvios() {
        return envioRepository.findAll();
    }

    public Optional<Envio> findEnvioById(Long id) {
        return envioRepository.findById(id);
    }

    public Optional<Envio> findEnvioByPedidoId(Long pedidoId) {
        return envioRepository.findByPedidoId(pedidoId);
    }

    public List<Envio> findEnviosByEstado(String estado) {
        return envioRepository.findByEstado(estado);
    }

    public List<Envio> findEnviosByRepartidor(Long repartidorId) {
        return envioRepository.findByRepartidorId(repartidorId);
    }

    public List<Envio> findEnviosByCodigoSeguimiento(String codigoSeguimiento) {
        return envioRepository.findByCodigoSeguimiento(codigoSeguimiento);
    }

    public Envio saveEnvio(Envio envio) {
        return envioRepository.save(envio);
    }

    public Optional<Envio> actualizarEstadoEnvio(Long id, String estado) {
        return envioRepository.findById(id)
                .map(envio -> {
                    envio.setEstado(estado);
                    if (estado.equalsIgnoreCase("ENVIADO")) {
                        envio.setFechaEnvio(LocalDateTime.now());
                    } else if (estado.equalsIgnoreCase("ENTREGADO")) {
                        envio.setFechaEntregaReal(LocalDateTime.now());
                    }
                    return envioRepository.save(envio);
                });
    }

    public void deleteEnvioById(Long id) {
        envioRepository.deleteById(id);
    }
} 