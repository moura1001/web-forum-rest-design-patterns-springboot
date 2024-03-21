package com.moura1001.webforum.service;

import com.moura1001.webforum.model.*;
import com.moura1001.webforum.service.storage.AchievementStorage;
import com.moura1001.webforum.service.storage.AchievementStorageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("forumServiceGamificationProxy")
public class ForumServiceGamificationProxy implements ForumService {

    @Autowired
    @Qualifier("forumServiceImpl")
    private ForumService forumServiceReal;

    @Autowired
    private AchievementStorageFactory achievementStorageFactory;

    @Override
    public Topico adicionarTopico(String usuario, String tituloTopico, String conteudoTopico) {
        Topico t = forumServiceReal.adicionarTopico(usuario, tituloTopico, conteudoTopico);

        AchievementStorage storage = achievementStorageFactory.getAchievementStorage();
        Usuario u = new Usuario(usuario, "");
        Points p = new Points("CREATION", u, 5l);
        Badge b = new Badge("I CAN TALK", u);
        storage.adicionarAchievement(usuario, p);
        storage.adicionarAchievement(usuario, b);

        return t;
    }

    @Override
    public List<Topico> obterTopicosCadastrados() {
        return forumServiceReal.obterTopicosCadastrados();
    }

    @Override
    public Topico obterTopico(Long topicoId) {
        return forumServiceReal.obterTopico(topicoId);
    }

    @Override
    public Comentario adicionarComentario(String usuario, Long topicoId, String comentario) {
        Comentario c = forumServiceReal.adicionarComentario(usuario, topicoId, comentario);

        AchievementStorage storage = achievementStorageFactory.getAchievementStorage();
        Usuario u = new Usuario(usuario, "");
        Points p = new Points("PARTICIPATION", u, 3l);
        Badge b = new Badge("LET ME ADD", u);
        storage.adicionarAchievement(usuario, p);
        storage.adicionarAchievement(usuario, b);

        return c;
    }

    public List<Comentario> obterComentariosCadastrados(Long topicoId) {
        return forumServiceReal.obterComentariosCadastrados(topicoId);
    }

    @Override
    public void gostarTopico(String usuario, Long topicoId, String usuarioTopico) {
        forumServiceReal.gostarTopico(usuario, topicoId, usuarioTopico);

        AchievementStorage storage = achievementStorageFactory.getAchievementStorage();
        Usuario u = new Usuario(usuario, "");
        Points p = new Points("CREATION", u, 1l);
        storage.adicionarAchievement(usuario, p);
    }

    @Override
    public void gostarComentario(String usuario, Long topicoId, Long comentarioId, String usuarioComentario) {
        forumServiceReal.gostarComentario(usuario, topicoId, comentarioId, usuarioComentario);

        AchievementStorage storage = achievementStorageFactory.getAchievementStorage();
        Usuario u = new Usuario(usuario, "");
        Points p = new Points("PARTICIPATION", u, 1l);
        storage.adicionarAchievement(usuario, p);
    }
}
