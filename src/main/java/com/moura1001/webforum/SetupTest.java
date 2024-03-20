package com.moura1001.webforum;

import com.moura1001.webforum.model.Achievement;
import com.moura1001.webforum.model.Badge;
import com.moura1001.webforum.model.Points;
import com.moura1001.webforum.model.Usuario;
import com.moura1001.webforum.repository.AchievementRepository;
import com.moura1001.webforum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SetupTest implements CommandLineRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Override
    public void run(String... args) throws Exception {
        Usuario usuario1 = new Usuario("user1", "Usuário 1");
        Usuario usuario2 = new Usuario("user2", "Usuário 2");

        List<Usuario> usuarios = List.of(usuario1, usuario2);
        usuarioRepository.saveAllAndFlush(usuarios);

        List<Achievement> achievements = List.of(
                new Points("POINTS_TYPE", usuario1, 100l),
                new Badge("BADGE_NAME", usuario1),
                new Points("POINTS_TYPE", usuario2, 50l),
                new Badge("BADGE_NAME", usuario2)
        );
        achievementRepository.saveAllAndFlush(achievements);
    }
}
