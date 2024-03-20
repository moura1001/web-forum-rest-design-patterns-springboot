package com.moura1001.webforum.service;

public interface ForumService {

    void adicionarTopico(String usuario, String tituloTopico, String conteudoTopico);

    void adicionarComentario(String usuario, Long topicoId, String comentario);

    void gostarTopico(String usuario, Long topicoId, String usuarioTopico);

    void gostarComentario(String usuario, Long topicoId, Long comentarioId, String usuarioComentario);

}
