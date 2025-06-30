package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.Usuario;
import com.proyectoSpring.fullstack.model.Rol;
import com.proyectoSpring.fullstack.model.TipoRol;
import com.proyectoSpring.fullstack.repository.UsuarioRepository;
import com.proyectoSpring.fullstack.repository.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario1;
    private Usuario usuario2;
    private Rol rolCliente;

    @BeforeEach
    void setUp() {
        usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Juan");
        usuario1.setEmail("juan@test.com");
        usuario1.setPassword("password123");
        usuario1.setRoles(new HashSet<>()); // Sin roles para que se asigne el rol por defecto

        usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("María");
        usuario2.setEmail("maria@test.com");
        usuario2.setPassword("password456");
        usuario2.setRoles(new HashSet<>()); // Sin roles para que se asigne el rol por defecto

        rolCliente = new Rol();
        rolCliente.setId(1L);
        rolCliente.setNombre(TipoRol.CLIENTE);
    }

    @Test
    void findAll_ShouldReturnAllUsuarios() {
        // Arrange
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // Act
        List<Usuario> result = usuarioService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(usuario1, result.get(0));
        assertEquals(usuario2, result.get(1));
        verify(usuarioRepository).findAll();
    }

    @Test
    void findById_WhenExists_ShouldReturnUsuario() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));

        // Act
        Optional<Usuario> result = usuarioService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(usuario1, result.get());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Usuario> result = usuarioService.findById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(usuarioRepository).findById(999L);
    }

    @Test
    void findByEmail_WhenExists_ShouldReturnUsuario() {
        // Arrange
        when(usuarioRepository.findByEmail("juan@test.com")).thenReturn(Optional.of(usuario1));

        // Act
        Optional<Usuario> result = usuarioService.findByEmail("juan@test.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(usuario1, result.get());
        verify(usuarioRepository).findByEmail("juan@test.com");
    }

    @Test
    void save_ShouldReturnSavedUsuario() {
        // Arrange
        lenient().when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        lenient().when(usuarioRepository.findByEmail(any())).thenReturn(Optional.empty());
        lenient().when(rolRepository.findByNombre(TipoRol.CLIENTE)).thenReturn(Optional.of(rolCliente));
        lenient().when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario1);

        // Act
        Usuario result = usuarioService.save(usuario1);

        // Assert
        assertEquals(usuario1, result);
        verify(usuarioRepository).save(usuario1);
    }

    @Test
    void update_WhenExists_ShouldReturnUpdatedUsuario() {
        // Arrange
        Usuario updatedUsuario = new Usuario();
        updatedUsuario.setId(1L);
        updatedUsuario.setNombre("Juan Updated");
        updatedUsuario.setEmail("juan.updated@test.com");
        updatedUsuario.setPassword("newPassword");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(updatedUsuario);

        // Act
        Usuario result = usuarioService.update(1L, updatedUsuario);

        // Assert
        assertEquals(updatedUsuario, result);
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void update_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            usuarioService.update(999L, usuario1);
        });
        verify(usuarioRepository).findById(999L);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deleteById_ShouldCallRepository() {
        // Arrange
        doNothing().when(usuarioRepository).deleteById(1L);

        // Act
        usuarioService.deleteById(1L);

        // Assert
        verify(usuarioRepository).deleteById(1L);
    }
} 