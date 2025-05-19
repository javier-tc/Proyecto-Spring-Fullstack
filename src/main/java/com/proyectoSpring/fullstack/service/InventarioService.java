package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.Inventario;
import com.proyectoSpring.fullstack.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {
    private final InventarioRepository inventarioRepository;

    @Autowired
    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> findById(Long id) {
        return inventarioRepository.findById(id);
    }

    public List<Inventario> findBySucursalId(Long sucursalId) {
        return inventarioRepository.findBySucursalId(sucursalId);
    }

    public Inventario save(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public void deleteById(Long id) {
        inventarioRepository.deleteById(id);
    }

    // Ajusta el stock de un producto en inventario y notifica si necesita
    // reposición.

    @Transactional
    public void ajustarStock(Long inventarioId, int cantidad) {
        inventarioRepository.findById(inventarioId).ifPresent(inventario -> {
            inventario.ajustarStock(cantidad);
            inventarioRepository.save(inventario);
            if (inventario.necesitaReposicion()) {
                // Aquí podrías enviar una notificación o registrar un evento
                System.out.println("¡Atención! El inventario necesita reposición.");
            }
        });
    }
}