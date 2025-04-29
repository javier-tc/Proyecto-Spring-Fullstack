package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.Usuario;
import com.proyectoSpring.fullstack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario save(Usuario usuario) {
        // Validar que el email no esté duplicado
        if (usuario.getId() == null && usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario update(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuarioActualizado.setId(id);
                    return usuarioRepository.save(usuarioActualizado);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
} 