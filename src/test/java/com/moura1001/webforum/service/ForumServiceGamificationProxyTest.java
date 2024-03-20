package com.moura1001.webforum.service;

import com.moura1001.webforum.model.Achievement;
import com.moura1001.webforum.model.Badge;
import com.moura1001.webforum.model.Points;
import com.moura1001.webforum.model.Usuario;
import com.moura1001.webforum.repository.AchievementRepository;
import com.moura1001.webforum.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ForumServiceGamificationProxyTest {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    @Qualifier("forumServiceGamificationProxy")
    private ForumService forumService;

    @BeforeAll
    void init() {
        Usuario usuario1 = new Usuario("user1", "Usuário 1");
        Usuario usuario2 = new Usuario("moura", "Genival Moura");
        Usuario usuario3 = new Usuario("user2", "Usuário 3");
        List<Usuario> usuarios = List.of(usuario1, usuario2, usuario3);
        usuarioRepository.saveAllAndFlush(usuarios);
    }

    @BeforeEach
    void setUp() {
        achievementRepository.deleteAll();
    }

    @Test
    void aoAdicionarUmTopicoPelaPrimeiraVezDeveGanharOBadgeICanTalk() {
        forumService.adicionarTopico("moura", "Meu primeiro tópico", "Conteúdo Tópico");
        List<Achievement> achievements = achievementRepository.findAllByLoginUsuario("moura");
        assertEquals(2, achievements.size());

        Optional<Achievement> achievement = achievementRepository.findByNomeToUsuario("CREATION", "moura");
        assertTrue(achievement.isPresent());
        assertInstanceOf(Points.class, achievement.get());
        Points points = (Points) achievement.get();
        assertEquals(5, points.getQuantidadePontos());

        achievement = achievementRepository.findByNomeToUsuario("I CAN TALK", "moura");
        assertTrue(achievement.isPresent());
        assertInstanceOf(Badge.class, achievement.get());
    }

    @Test
    void aoAdicionarVariosTopicosDeveAcumularOsPontosDoUsuario() {
        forumService.adicionarTopico("moura", "Meu primeiro tópico", "Conteúdo...");
        forumService.adicionarTopico("moura", "Meu segundo tópico", "Conteúdo...");
        forumService.adicionarTopico("moura", "Meu terceiro tópico", "Conteúdo...");
        List<Achievement> achievements = achievementRepository.findAllByLoginUsuario("moura");
        assertEquals(2, achievements.size());

        Optional<Achievement> achievement = achievementRepository.findByNomeToUsuario("CREATION", "moura");
        assertTrue(achievement.isPresent());
        assertInstanceOf(Points.class, achievement.get());
        Points points = (Points) achievement.get();
        assertEquals(15, points.getQuantidadePontos());

        achievement = achievementRepository.findByNomeToUsuario("I CAN TALK", "moura");
        assertNotNull(achievement);
        assertInstanceOf(Badge.class, achievement.get());
    }
}
