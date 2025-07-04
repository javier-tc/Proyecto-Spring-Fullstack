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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticación", description = "Endpoints para autenticación y registro de usuarios")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final UsuarioService usuarioService;

    @Autowired
    public AuthController(AuthService authService, UsuarioService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica un usuario con email y contraseña, devolviendo un token JWT"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Login exitoso",
            content = @Content(schema = @Schema(implementation = LoginResponse.class))
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Credenciales inválidas"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de entrada inválidos"
        )
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registro")
    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea una nueva cuenta de usuario en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Usuario registrado exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de entrada inválidos o email ya existe"
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    public ResponseEntity<?> registro(@Valid @RequestBody RegistroRequest registroRequest) {
        logger.info("Intentando registrar nuevo usuario: {}", registroRequest.getEmail());
        try {
            Usuario usuario = new Usuario();
            usuario.setEmail(registroRequest.getEmail());
            usuario.setPassword(registroRequest.getPassword());
            usuario.setNombre(registroRequest.getNombre());
            usuario.setApellido(registroRequest.getApellido());
            
            Usuario usuarioGuardado = usuarioService.save(usuario);
            logger.info("Usuario registrado exitosamente: {}", usuarioGuardado.getEmail());
            return ResponseEntity.ok(usuarioGuardado);
        } catch (Exception e) {
            logger.error("Error al registrar usuario: {}", registroRequest.getEmail(), e);
            throw e; // Propagar la excepción para que sea manejada por GlobalExceptionHandler
        }
    }
} 