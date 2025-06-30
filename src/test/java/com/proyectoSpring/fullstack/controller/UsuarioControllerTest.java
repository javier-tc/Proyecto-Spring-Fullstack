package com.proyectoSpring.fullstack.controller;

import com.proyectoSpring.fullstack.assembler.UsuarioAssembler;
import com.proyectoSpring.fullstack.model.Usuario;
import com.proyectoSpring.fullstack.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioAssembler usuarioAssembler;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario1;
    private Usuario usuario2;
    private EntityModel<Usuario> entityModel1;
    private EntityModel<Usuario> entityModel2;

    @BeforeEach
    void setUp() {
        usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Juan");
        usuario1.setEmail("juan@test.com");

        usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("María");
        usuario2.setEmail("maria@test.com");

        entityModel1 = EntityModel.of(usuario1);
        entityModel2 = EntityModel.of(usuario2);
    }

    @Test
    void getAllUsuarios_ShouldReturnCollectionModel() {
        // Arrange
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        when(usuarioService.findAll()).thenReturn(usuarios);
        when(usuarioAssembler.toModel(usuario1)).thenReturn(entityModel1);
        when(usuarioAssembler.toModel(usuario2)).thenReturn(entityModel2);

        // Act
        CollectionModel<EntityModel<Usuario>> result = usuarioController.getAllUsuarios();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(usuarioService).findAll();
        verify(usuarioAssembler, times(2)).toModel(any(Usuario.class));
    }

    @Test
    void getUsuarioById_WhenExists_ShouldReturnUsuario() {
        // Arrange
        when(usuarioService.findById(1L)).thenReturn(Optional.of(usuario1));
        when(usuarioAssembler.toModel(usuario1)).thenReturn(entityModel1);

        // Act
        ResponseEntity<EntityModel<Usuario>> result = usuarioController.getUsuarioById(1L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(usuarioService).findById(1L);
        verify(usuarioAssembler).toModel(usuario1);
    }

    @Test
    void getUsuarioById_WhenNotExists_ShouldReturnNotFound() {
        // Arrange
        when(usuarioService.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<EntityModel<Usuario>> result = usuarioController.getUsuarioById(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(usuarioService).findById(999L);
        verify(usuarioAssembler, never()).toModel(any());
    }

    @Test
    void getUsuarioByEmail_WhenExists_ShouldReturnUsuario() {
        // Arrange
        when(usuarioService.findByEmail("juan@test.com")).thenReturn(Optional.of(usuario1));
        when(usuarioAssembler.toModel(usuario1)).thenReturn(entityModel1);

        // Act
        ResponseEntity<EntityModel<Usuario>> result = usuarioController.getUsuarioByEmail("juan@test.com");

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(usuarioService).findByEmail("juan@test.com");
        verify(usuarioAssembler).toModel(usuario1);
    }

    @Test
    void createUsuario_ShouldReturnCreatedUsuario() {
        // Arrange
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario1);
        when(usuarioAssembler.toModel(usuario1)).thenReturn(entityModel1);

        // Act
        ResponseEntity<EntityModel<Usuario>> result = usuarioController.createUsuario(usuario1);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(usuarioService).save(usuario1);
        verify(usuarioAssembler).toModel(usuario1);
    }

    @Test
    void updateUsuario_WhenExists_ShouldReturnUpdatedUsuario() {
        // Arrange
        when(usuarioService.update(1L, usuario1)).thenReturn(usuario1);
        when(usuarioAssembler.toModel(usuario1)).thenReturn(entityModel1);

        // Act
        ResponseEntity<EntityModel<Usuario>> result = usuarioController.updateUsuario(1L, usuario1);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(usuarioService).update(1L, usuario1);
        verify(usuarioAssembler).toModel(usuario1);
    }

    @Test
    void deleteUsuario_ShouldReturnOk() {
        // Arrange
        doNothing().when(usuarioService).deleteById(1L);

        // Act
        ResponseEntity<Void> result = usuarioController.deleteUsuario(1L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(usuarioService).deleteById(1L);
    }
} 