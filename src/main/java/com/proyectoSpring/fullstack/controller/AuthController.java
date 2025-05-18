package com.proyectoSpring.fullstack.controller;

import com.proyectoSpring.fullstack.dto.LoginRequest;
import com.proyectoSpring.fullstack.dto.LoginResponse;
import com.proyectoSpring.fullstack.dto.RegistroRequest;
import com.proyectoSpring.fullstack.model.Usuario;
import com.proyectoSpring.fullstack.service.AuthService;
import com.proyectoSpring.fullstack.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    @Autowired
    public AuthController(AuthService authService, UsuarioService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@Valid @RequestBody RegistroRequest registroRequest) {
        try {
            Usuario usuario = new Usuario();
            usuario.setEmail(registroRequest.getEmail());
            usuario.setPassword(registroRequest.getPassword());
            usuario.setNombre(registroRequest.getNombre());
            usuario.setApellido(registroRequest.getApellido());
            
            Usuario usuarioGuardado = usuarioService.save(usuario);
            return ResponseEntity.ok(usuarioGuardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 