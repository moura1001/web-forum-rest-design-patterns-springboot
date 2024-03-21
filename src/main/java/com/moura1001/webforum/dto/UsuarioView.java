package com.moura1001.webforum.dto;

import com.moura1001.webforum.model.Usuario;

public record UsuarioView(
        String login,
        String nome
) {
    public UsuarioView(Usuario usuario) {
        this(
                usuario.getLogin(),
                usuario.getNome()
        );
    }
}
