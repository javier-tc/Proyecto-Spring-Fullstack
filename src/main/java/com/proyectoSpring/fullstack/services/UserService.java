package com.proyectoSpring.fullstack.services;

import com.proyectoSpring.fullstack.model.Usuario;
import com.proyectoSpring.fullstack.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Usuario> listarUsuarios() {
        return userRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuario(Long id) {
        return userRepository.findById(id);
    }

    public Usuario crearUsuario(Usuario usuario) {
        return userRepository.save(usuario);
    }

    public Optional<Usuario> actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return userRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setPassword(usuarioActualizado.getPassword());
            usuario.setTipoUsuario(usuarioActualizado.getTipoUsuario());
            usuario.setRoles(usuarioActualizado.getRoles());
            usuario.setActivo(usuarioActualizado.isActivo());
            return userRepository.save(usuario);
        });
    }

    public boolean eliminarUsuario(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}