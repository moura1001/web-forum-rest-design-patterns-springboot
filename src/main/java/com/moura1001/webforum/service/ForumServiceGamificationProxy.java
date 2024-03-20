package com.moura1001.webforum.service;

import com.moura1001.webforum.model.Achievement;
import com.moura1001.webforum.model.Badge;
import com.moura1001.webforum.model.Points;
import com.moura1001.webforum.model.Usuario;
import com.moura1001.webforum.repository.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("forumServiceGamificationProxy")
public class ForumServiceGamificationProxy implements ForumService {

    @Autowired
    @Qualifier("forumServiceImpl")
    private ForumService forumServiceReal;

    @Autowired
    private AchievementRepository achievementRepository;

    @Override
    public void adicionarTopico(String usuario, String tituloTopico, String conteudoTopico) {
        forumServiceReal.adicionarTopico(usuario, tituloTopico, conteudoTopico);

        Usuario u = new Usuario(usuario, "");
        Points p = new Points("CREATION", u, 5l);

        Optional<Achievement> achievement =  achievementRepository.findByNomeToUsuario("CREATION", usuario);
        if (achievement.isEmpty())
            achievementRepository.save(p);
        else {
            Achievement a = achievement.get();
            a.adicionar(p);
            achievementRepository.save(a);
        }

        if (!achievementRepository.existsByNomeToUsuario("I CAN TALK", usuario))
            achievementRepository.save(new Badge("I CAN TALK", u));
    }

    @Override
    public void adicionarComentario(String usuario, Long topicoId, String comentario) {

    }

    @Override
    public void gostarTopico(String usuario, Long topicoId, String usuarioTopico) {

    }

    @Override
    public void gostarComentario(String usuario, Long topicoId, Long comentarioId, String usuarioComentario) {

    }
}
