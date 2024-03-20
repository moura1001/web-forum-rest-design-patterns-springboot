package com.moura1001.webforum.service;

import com.moura1001.webforum.model.Badge;
import com.moura1001.webforum.model.Points;
import com.moura1001.webforum.model.Usuario;
import com.moura1001.webforum.service.storage.AchievementStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("forumServiceGamificationProxy")
public class ForumServiceGamificationProxy implements ForumService {

    @Autowired
    @Qualifier("forumServiceImpl")
    private ForumService forumServiceReal;

    @Autowired
    @Qualifier("relationalDatabaseAchievementStorage")
    private AchievementStorage achievementStorage;

    @Override
    public void adicionarTopico(String usuario, String tituloTopico, String conteudoTopico) {
        forumServiceReal.adicionarTopico(usuario, tituloTopico, conteudoTopico);

        Usuario u = new Usuario(usuario, "");
        Points p = new Points("CREATION", u, 5l);
        Badge b = new Badge("I CAN TALK", u);
        achievementStorage.adicionarAchievement(usuario, p);
        achievementStorage.adicionarAchievement(usuario, b);
    }

    @Override
    public void adicionarComentario(String usuario, Long topicoId, String comentario) {
        forumServiceReal.adicionarComentario(usuario, topicoId, comentario);

        Usuario u = new Usuario(usuario, "");
        Points p = new Points("PARTICIPATION", u, 3l);
        Badge b = new Badge("LET ME ADD", u);
        achievementStorage.adicionarAchievement(usuario, p);
        achievementStorage.adicionarAchievement(usuario, b);
    }

    @Override
    public void gostarTopico(String usuario, Long topicoId, String usuarioTopico) {
        forumServiceReal.gostarTopico(usuario, topicoId, usuarioTopico);

        Usuario u = new Usuario(usuario, "");
        Points p = new Points("CREATION", u, 1l);
        achievementStorage.adicionarAchievement(usuario, p);
    }

    @Override
    public void gostarComentario(String usuario, Long topicoId, Long comentarioId, String usuarioComentario) {
        forumServiceReal.gostarComentario(usuario, topicoId, comentarioId, usuarioComentario);

        Usuario u = new Usuario(usuario, "");
        Points p = new Points("PARTICIPATION", u, 1l);
        achievementStorage.adicionarAchievement(usuario, p);
    }
}
