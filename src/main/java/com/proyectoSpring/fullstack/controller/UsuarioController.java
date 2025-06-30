package com.proyectoSpring.fullstack.controller;

import com.proyectoSpring.fullstack.assembler.UsuarioAssembler;
import com.proyectoSpring.fullstack.model.Usuario;
import com.proyectoSpring.fullstack.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioAssembler usuarioAssembler;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, UsuarioAssembler usuarioAssembler) {
        this.usuarioService = usuarioService;
        this.usuarioAssembler = usuarioAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Usuario>> getAllUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.findAll().stream()
                .map(usuarioAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(usuarioAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioByEmail(@PathVariable String email) {
        return usuarioService.findByEmail(email)
                .map(usuarioAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> createUsuario(@RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(usuarioAssembler.toModel(savedUsuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario updatedUsuario = usuarioService.update(id, usuario);
        return ResponseEntity.ok(usuarioAssembler.toModel(updatedUsuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 