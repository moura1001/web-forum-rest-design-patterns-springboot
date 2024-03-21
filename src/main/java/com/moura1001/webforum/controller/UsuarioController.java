package com.moura1001.webforum.controller;

import com.moura1001.webforum.dto.UsuarioDTO;
import com.moura1001.webforum.dto.UsuarioView;
import com.moura1001.webforum.model.Usuario;
import com.moura1001.webforum.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/forum/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioView> salvarUsuario(@RequestBody @Valid UsuarioDTO usuario) {
        Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario.toEntity());
        return ResponseEntity.created(
                URI.create("/forum/api/v1/usuarios?login="+usuarioSalvo.getLogin())
        ).body(new UsuarioView(usuarioSalvo));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioView>> obterUsuarios() {
        List<UsuarioView> usuarios = usuarioService.obterUsuariosCadastrados().stream()
                .map((usuario) -> new UsuarioView(usuario)).collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }
}
