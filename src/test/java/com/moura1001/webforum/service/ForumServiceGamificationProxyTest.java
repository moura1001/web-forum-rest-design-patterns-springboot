package com.moura1001.webforum.service;

import com.moura1001.webforum.model.Achievement;
import com.moura1001.webforum.model.Badge;
import com.moura1001.webforum.model.Points;
import com.moura1001.webforum.model.Usuario;
import com.moura1001.webforum.repository.AchievementRepository;
import com.moura1001.webforum.repository.UsuarioRepository;
import com.moura1001.webforum.service.storage.AchievementStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

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
    @Qualifier("relationalDatabaseAchievementStorage")
    private AchievementStorage achievementStorage;

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
        List<Achievement> achievements = achievementStorage.getAllAchievements("moura");
        assertEquals(2, achievements.size());

        Achievement achievement = achievementStorage.getAchievement("moura", "CREATION");
        assertNotNull(achievement);
        assertInstanceOf(Points.class, achievement);
        Points points = (Points) achievement;
        assertEquals(5, points.getQuantidadePontos());

        achievement = achievementStorage.getAchievement("moura", "I CAN TALK");
        assertNotNull(achievement);
        assertInstanceOf(Badge.class, achievement);
    }

    @Test
    void aoAdicionarVariosTopicosDeveAcumularOsPontosDoUsuario() {
        forumService.adicionarTopico("moura", "Meu primeiro tópico", "Conteúdo...");
        forumService.adicionarTopico("moura", "Meu segundo tópico", "Conteúdo...");
        forumService.adicionarTopico("moura", "Meu terceiro tópico", "Conteúdo...");
        List<Achievement> achievements = achievementStorage.getAllAchievements("moura");
        assertEquals(2, achievements.size());

        Achievement achievement = achievementStorage.getAchievement("moura", "CREATION");
        assertNotNull(achievement);
        assertInstanceOf(Points.class, achievement);
        Points points = (Points) achievement;
        assertEquals(15, points.getQuantidadePontos());

        achievement = achievementStorage.getAchievement("moura", "I CAN TALK");
        assertNotNull(achievement);
        assertInstanceOf(Badge.class, achievement);
    }
}
