package com.moura1001.webforum.service;

import com.moura1001.webforum.model.Badge;
import com.moura1001.webforum.model.Points;
import com.moura1001.webforum.model.Usuario;
import com.moura1001.webforum.service.storage.AchievementStorage;
import com.moura1001.webforum.service.storage.AchievementStorageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("forumServiceGamificationProxy")
public class ForumServiceGamificationProxy implements ForumService {

    @Autowired
    @Qualifier("forumServiceImpl")
    private ForumService forumServiceReal;

    @Autowired
    private AchievementStorageFactory achievementStorageFactory;

    @Override
    public void adicionarTopico(String usuario, String tituloTopico, String conteudoTopico) {
        forumServiceReal.adicionarTopico(usuario, tituloTopico, conteudoTopico);

        AchievementStorage storage = achievementStorageFactory.getAchievementStorage();
        Usuario u = new Usuario(usuario, "");
        Points p = new Points("CREATION", u, 5l);
        Badge b = new Badge("I CAN TALK", u);
        storage.adicionarAchievement(usuario, p);
        storage.adicionarAchievement(usuario, b);
    }

    @Override
    public void adicionarComentario(String usuario, Long topicoId, String comentario) {
        forumServiceReal.adicionarComentario(usuario, topicoId, comentario);

        AchievementStorage storage = achievementStorageFactory.getAchievementStorage();
        Usuario u = new Usuario(usuario, "");
        Points p = new Points("PARTICIPATION", u, 3l);
        Badge b = new Badge("LET ME ADD", u);
        storage.adicionarAchievement(usuario, p);
        storage.adicionarAchievement(usuario, b);
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
