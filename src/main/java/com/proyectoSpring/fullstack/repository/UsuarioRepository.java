package com.proyectoSpring.fullstack.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectoSpring.fullstack.model.Usuario;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {
    private final String FILE_PATH = "usuarios.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Usuario> usuarios;

    public UsuarioRepository() {
        this.usuarios = new ArrayList<>();
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                usuarios = objectMapper.readValue(file, new TypeReference<List<Usuario>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarUsuarios() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> findAll() {
        return new ArrayList<>(usuarios);
    }

    public Optional<Usuario> findById(Long id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(generarNuevoId());
            usuarios.add(usuario);
        } else {
            usuarios.removeIf(u -> u.getId().equals(usuario.getId()));
            usuarios.add(usuario);
        }
        guardarUsuarios();
        return usuario;
    }

    public void deleteById(Long id) {
        usuarios.removeIf(u -> u.getId().equals(id));
        guardarUsuarios();
    }

    private Long generarNuevoId() {
        return usuarios.stream()
                .mapToLong(Usuario::getId)
                .max()
                .orElse(0) + 1;
    }
} 