package com.proyectoSpring.fullstack;

import com.proyectoSpring.fullstack.model.Usuario;
import com.proyectoSpring.fullstack.model.Rol;
import com.proyectoSpring.fullstack.model.Sucursal;
import com.proyectoSpring.fullstack.model.TipoRol;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioTest {

    @Test
    public void testCrearUsuario() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setApellido("Usuario");
        usuario.setEmail("test@perfulandia.com");
        usuario.setPassword("password123");
        usuario.setActivo(true);

        // Act & Assert
        assertNotNull(usuario);
        assertEquals("Test", usuario.getNombre());
        assertEquals("Usuario", usuario.getApellido());
        assertEquals("test@perfulandia.com", usuario.getEmail());
        assertTrue(usuario.isActivo());
    }

    @Test
    public void testUsuarioConRoles() {
        // Arrange
        Usuario usuario = new Usuario();
        Set<Rol> roles = new HashSet<>();
        Rol rolAdmin = new Rol();
        rolAdmin.setNombre(TipoRol.ADMINISTRADOR);
        roles.add(rolAdmin);
        usuario.setRoles(roles);

        // Act & Assert
        assertNotNull(usuario.getRoles());
        assertEquals(1, usuario.getRoles().size());
        assertTrue(usuario.getRoles().stream()
                .anyMatch(rol -> rol.getNombre().equals(TipoRol.ADMINISTRADOR)));
    }

    @Test
    public void testUsuarioConSucursal() {
        // Arrange
        Usuario usuario = new Usuario();
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Sucursal Test");
        usuario.setSucursal(sucursal);

        // Act & Assert
        assertNotNull(usuario.getSucursal());
        assertEquals(1L, usuario.getSucursal().getId());
        assertEquals("Sucursal Test", usuario.getSucursal().getNombre());
    }

    @Test
    public void testUsuarioInactivo() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setActivo(false);

        // Act & Assert
        assertFalse(usuario.isActivo());
    }
} 