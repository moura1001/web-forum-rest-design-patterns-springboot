package com.moura1001.webforum.dto;

import com.moura1001.webforum.model.Topico;

public record TopicoViewCreate(
        String login,
        String nome,
        String titulo,
        Long topicoId
) {
    public TopicoViewCreate(Topico topico) {
        this(
                topico.getUsuario().getLogin(),
                topico.getUsuario().getNome(),
                topico.getTitulo(),
                topico.getId()
        );
    }
}
