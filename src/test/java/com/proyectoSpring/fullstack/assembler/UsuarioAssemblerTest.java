package com.proyectoSpring.fullstack.assembler;

import com.proyectoSpring.fullstack.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioAssemblerTest {

    private UsuarioAssembler usuarioAssembler;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuarioAssembler = new UsuarioAssembler();
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setEmail("juan@test.com");
    }

    @Test
    void toModel_ShouldReturnEntityModel() {
        // Act
        EntityModel<Usuario> result = usuarioAssembler.toModel(usuario);

        // Assert
        assertNotNull(result);
        assertEquals(usuario, result.getContent());
        assertNotNull(result.getLinks());
    }

    @Test
    void toModel_ShouldContainSelfLink() {
        // Act
        EntityModel<Usuario> result = usuarioAssembler.toModel(usuario);

        // Assert
        assertTrue(result.hasLink("self"));
    }

    @Test
    void toModel_ShouldContainUsuariosLink() {
        // Act
        EntityModel<Usuario> result = usuarioAssembler.toModel(usuario);

        // Assert
        assertTrue(result.hasLink("usuarios"));
    }

    @Test
    void toModel_ShouldContainUsuarioByEmailLink() {
        // Act
        EntityModel<Usuario> result = usuarioAssembler.toModel(usuario);

        // Assert
        assertTrue(result.hasLink("usuarioByEmail"));
    }

    @Test
    void toModel_WithNullUsuario_ShouldThrowException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            usuarioAssembler.toModel(null);
        });
    }
} 