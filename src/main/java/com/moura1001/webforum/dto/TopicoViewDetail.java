package com.moura1001.webforum.dto;

import com.moura1001.webforum.model.Topico;

import java.util.List;

public record TopicoViewDetail(
        String login,
        String nome,
        String titulo,
        String conteudo,
        Long topicoId,
        List<ComentarioView> comentarios
) {
    public TopicoViewDetail(Topico topico, List<ComentarioView> comentarios) {
        this(
                topico.getUsuario().getLogin(),
                topico.getUsuario().getNome(),
                topico.getTitulo(),
                topico.getConteudo(),
                topico.getId(),
                comentarios
        );
    }
}
