package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.dto.LoginRequest;
import com.proyectoSpring.fullstack.dto.LoginResponse;
import com.proyectoSpring.fullstack.model.Usuario;
import com.proyectoSpring.fullstack.repository.UsuarioRepository;
import com.proyectoSpring.fullstack.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                      JwtTokenProvider jwtTokenProvider,
                      UsuarioRepository usuarioRepository,
                      UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + loginRequest.getEmail()));

            if (!usuario.isActivo()) {
                throw new BadCredentialsException("La cuenta está desactivada");
            }

            if (usuario.isBloqueado()) {
                throw new BadCredentialsException("La cuenta está bloqueada por múltiples intentos fallidos");
            }

            String token = jwtTokenProvider.generateToken(authentication);
            usuarioService.restablecerIntentosFallidos(usuario.getEmail());

            return new LoginResponse(
                token,
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getApellido()
            );
        } catch (AuthenticationException e) {
            usuarioService.registrarIntentoFallido(loginRequest.getEmail());
            throw e;
        }
    }
} 