package com.moura1001.webforum.service;

import com.moura1001.webforum.model.Comentario;
import com.moura1001.webforum.model.Topico;

import java.util.List;

public interface ForumService {

    Topico adicionarTopico(String usuario, String tituloTopico, String conteudoTopico);

    List<Topico> obterTopicosCadastrados();

    Topico obterTopico(Long topicoId);

    Comentario adicionarComentario(String usuario, Long topicoId, String comentario);

    List<Comentario> obterComentariosCadastrados(Long topicoId);

    Comentario obterComentario(Long comentarioId);

    void gostarTopico(String usuario, Long topicoId, String usuarioTopico);

    void gostarComentario(String usuario, Long topicoId, Long comentarioId, String usuarioComentario);

}
