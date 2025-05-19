package com.proyectoSpring.fullstack.service;

import com.proyectoSpring.fullstack.model.Usuario;
import com.proyectoSpring.fullstack.model.Rol;
import com.proyectoSpring.fullstack.model.TipoRol;
import com.proyectoSpring.fullstack.repository.UsuarioRepository;
import com.proyectoSpring.fullstack.repository.RolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        logger.info("Intentando guardar usuario: {}", usuario.getEmail());
        try {
            // Validar email único
            if (usuario.getId() == null && usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
                logger.warn("El email {} ya está registrado", usuario.getEmail());
                throw new RuntimeException("El email ya está registrado");
            }

            // Encriptar contraseña si es un nuevo usuario o si se cambió
            if (usuario.getId() == null || !usuario.getPassword().startsWith("$2a$")) {
                logger.debug("Encriptando contraseña para usuario: {}", usuario.getEmail());
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }

            // Si no se especifican roles, asignar rol CLIENTE por defecto
            if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
                logger.debug("Asignando rol CLIENTE por defecto a usuario: {}", usuario.getEmail());
                Set<Rol> roles = new HashSet<>();
                rolRepository.findByNombre(TipoRol.CLIENTE)
                    .ifPresent(roles::add);
                usuario.setRoles(roles);
            }

            Usuario savedUsuario = usuarioRepository.save(usuario);
            logger.info("Usuario guardado exitosamente: {}", savedUsuario.getEmail());
            return savedUsuario;
        } catch (Exception e) {
            logger.error("Error al guardar usuario: {}", usuario.getEmail(), e);
            throw e;
        }
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public Usuario update(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    // Mantener el ID original
                    usuarioActualizado.setId(id);

                    // Si la contraseña no está encriptada, encriptarla
                    if (!usuarioActualizado.getPassword().startsWith("$2a$")) {
                        usuarioActualizado.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
                    } else {
                        // Si ya está encriptada, mantener la contraseña existente
                        usuarioActualizado.setPassword(usuarioExistente.getPassword());
                    }

                    return usuarioRepository.save(usuarioActualizado);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Añadido: Incrementa los intentos fallidos y bloquea el usuario si supera el
    // límite.

    @Transactional
    public void registrarIntentoFallido(String email) {
        usuarioRepository.findByEmail(email).ifPresent(usuario -> {
            int intentos = usuario.getIntentosFallidos() + 1;
            usuario.setIntentosFallidos(intentos);
            if (intentos >= 5) {
                usuario.setBloqueado(true); // Bloquear al usuario
            }
            usuarioRepository.save(usuario);
        });
    }

    // Añadido: Restablece los intentos fallidos tras un login exitoso.

    @Transactional
    public void restablecerIntentosFallidos(String email) {
        usuarioRepository.findByEmail(email).ifPresent(usuario -> {
            usuario.setIntentosFallidos(0);
            usuarioRepository.save(usuario);
        });
    }
}