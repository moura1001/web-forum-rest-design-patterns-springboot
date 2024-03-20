package com.moura1001.webforum.service;

import com.moura1001.webforum.model.*;
import com.moura1001.webforum.repository.AchievementRepository;
import com.moura1001.webforum.repository.TopicoRepository;
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
    private TopicoRepository topicoRepository;
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
        Topico topico1 = new Topico(1l, "Tópico 1", "Conteúdo 1", usuario1);
        Topico topico2 = new Topico(2l, "Tópico 2", "Conteúdo 2", usuario3);
        List<Topico> topicos = List.of(topico1, topico2);
        topicoRepository.saveAllAndFlush(topicos);
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

    @Test
    void aoAdicionarUmComentarioPelaPrimeiraVezDeveGanharOBadgeLetMeAdd() {
        forumService.adicionarComentario("moura", 1l, "Meu primeiro comentário");
        List<Achievement> achievements = achievementStorage.getAllAchievements("moura");
        assertEquals(2, achievements.size());

        Achievement achievement = achievementStorage.getAchievement("moura", "PARTICIPATION");
        assertNotNull(achievement);
        assertInstanceOf(Points.class, achievement);
        Points points = (Points) achievement;
        assertEquals(3, points.getQuantidadePontos());

        achievement = achievementStorage.getAchievement("moura", "LET ME ADD");
        assertNotNull(achievement);
        assertInstanceOf(Badge.class, achievement);
    }

    @Test
    void aoCurtirUmTopicoDeveAdicionar1PontoDoTipoCreation() {
        forumService.gostarTopico("moura", 2l, "randomUser");
        List<Achievement> achievements = achievementStorage.getAllAchievements("moura");
        assertEquals(1, achievements.size());

        Achievement achievement = achievementStorage.getAchievement("moura", "CREATION");
        assertNotNull(achievement);
        assertInstanceOf(Points.class, achievement);
        Points points = (Points) achievement;
        assertEquals(1, points.getQuantidadePontos());
    }

    @Test
    void aoCurtirUmComentarioDeveAdicionar1PontoDoTipoParticipation() {
        forumService.gostarComentario("moura", 1l, 1l, "randomUser");
        List<Achievement> achievements = achievementStorage.getAllAchievements("moura");
        assertEquals(1, achievements.size());

        Achievement achievement = achievementStorage.getAchievement("moura", "PARTICIPATION");
        assertNotNull(achievement);
        assertInstanceOf(Points.class, achievement);
        Points points = (Points) achievement;
        assertEquals(1, points.getQuantidadePontos());
    }

    @Test
    void deveGanharTodosOsBadgesRelacionadosAAlgumaDeSuasInteracoesNoForum() {
        forumService.adicionarTopico("moura", "Meu primeiro tópico", "Conteúdo...");
        forumService.adicionarComentario("moura", 1l, "Meu primeiro comentário");
        forumService.adicionarTopico("moura", "Meu segundo tópico", "Conteúdo...");
        forumService.gostarTopico("moura", 2l, "randomUser");
        forumService.gostarComentario("moura", 2l, 1l, "randomUser");
        List<Achievement> achievements = achievementStorage.getAllAchievements("moura");
        assertEquals(4, achievements.size());

        Achievement achievement = achievementStorage.getAchievement("moura", "CREATION");
        assertNotNull(achievement);
        assertInstanceOf(Points.class, achievement);
        Points points = (Points) achievement;
        assertEquals(11, points.getQuantidadePontos());

        achievement = achievementStorage.getAchievement("moura", "PARTICIPATION");
        assertNotNull(achievement);
        assertInstanceOf(Points.class, achievement);
        points = (Points) achievement;
        assertEquals(4, points.getQuantidadePontos());

        achievement = achievementStorage.getAchievement("moura", "I CAN TALK");
        assertNotNull(achievement);
        assertInstanceOf(Badge.class, achievement);

        achievement = achievementStorage.getAchievement("moura", "LET ME ADD");
        assertNotNull(achievement);
        assertInstanceOf(Badge.class, achievement);
    }
}
