package com.moura1001.webforum.service;

import com.moura1001.webforum.model.Comentario;
import com.moura1001.webforum.model.Topico;
import com.moura1001.webforum.model.Usuario;
import com.moura1001.webforum.repository.ComentarioRepository;
import com.moura1001.webforum.repository.TopicoRepository;
import com.moura1001.webforum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("forumServiceImpl")
public class ForumServiceImpl implements ForumService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    public Topico adicionarTopico(String usuario, String tituloTopico, String conteudoTopico) {
        Usuario u = usuarioRepository.findByLogin(usuario).orElseThrow(() -> {
            throw new RuntimeException("usuário não existe");
        });
        return topicoRepository.save(new Topico(tituloTopico, conteudoTopico, u));
    }

    @Override
    public List<Topico> obterTopicosCadastrados() {
        return topicoRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Topico obterTopico(Long topicoId) {
        return topicoRepository.findById(topicoId).orElseThrow(() -> {
            throw new RuntimeException("tópico não existe");
        });
    }

    @Override
    public Comentario adicionarComentario(String usuario, Long topicoId, String comentario) {
        Usuario u = usuarioRepository.findByLogin(usuario).orElseThrow(() -> {
            throw new RuntimeException("usuário não existe");
        });
        Topico t = topicoRepository.findById(topicoId).orElseThrow(() -> {
            throw new RuntimeException("tópico não existe");
        });
        return comentarioRepository.save(new Comentario(comentario, u, t));
    }

    public List<Comentario> obterComentariosCadastrados(Long topicoId) {
        return comentarioRepository.findAllByTopicoId(topicoId);
    }

    @Override
    public Comentario obterComentario(Long comentarioId) {
        return comentarioRepository.findById(comentarioId).orElseThrow(() -> {
            throw new RuntimeException("comentário não existe");
        });
    }

    @Override
    public void gostarTopico(String usuario, Long topicoId, String usuarioTopico) {

    }

    @Override
    public void gostarComentario(String usuario, Long topicoId, Long comentarioId, String usuarioComentario) {

    }
}
