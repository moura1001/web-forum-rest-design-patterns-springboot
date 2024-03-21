package com.moura1001.webforum.dto;

import com.moura1001.webforum.model.Comentario;

public record ComentarioView(
        String login,
        String nome,
        String conteudo,
        Long comentarioId
) {
    public ComentarioView(Comentario comentario) {
        this(
                comentario.getUsuario().getLogin(),
                comentario.getUsuario().getNome(),
                comentario.getConteudo(),
                comentario.getId()
        );
    }
}
