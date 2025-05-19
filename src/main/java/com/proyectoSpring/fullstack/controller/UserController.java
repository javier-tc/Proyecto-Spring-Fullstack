package com.proyectoSpring.fullstack.controller;

import com.proyectoSpring.fullstack.model.Usuario;
import com.proyectoSpring.fullstack.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Obtiene la lista de todos los usuarios.
     */
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return userService.listarUsuarios();
    }

    /**
     * Obtiene un usuario por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = userService.obtenerUsuario(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo usuario.
     */
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return userService.crearUsuario(usuario);
    }

    /**
     * Actualiza un usuario existente por su ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuario = userService.actualizarUsuario(id, usuarioActualizado);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Elimina un usuario por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = userService.eliminarUsuario(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}