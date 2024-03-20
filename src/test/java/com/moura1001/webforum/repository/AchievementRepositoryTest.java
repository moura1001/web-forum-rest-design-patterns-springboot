package com.moura1001.webforum.repository;

import com.moura1001.webforum.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AchievementRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    private static final Usuario usuario1 = new Usuario("user1", "Usuário 1");
    private static final Usuario usuario2 = new Usuario("user2", "Usuário 2");

    @BeforeAll
    void init() {
        achievementRepository.deleteAll();
        usuarioRepository.deleteAll();
        List<Usuario> usuarios = List.of(usuario1, usuario2);
        usuarioRepository.saveAllAndFlush(usuarios);
    }

    @BeforeEach
    void setUp() {
        achievementRepository.deleteAll();
        List<Achievement> achievements = List.of(
                new Points("POINTS_TYPE", usuario1, 100l),
                new Badge("BADGE_NAME", usuario1),
                new Points("POINTS_TYPE", usuario2, 50l),
                new Badge("BADGE_NAME", usuario2)
        );
        achievementRepository.saveAllAndFlush(achievements);
    }

    @Test
    public void deveListarTodosOsAchievementsObtidos() throws InterruptedException {
        List<Achievement> achievements = achievementRepository.findAllByLoginUsuario("user1");
        assertEquals(2, achievements.size());
        assertEquals("user1", achievements.get(0).getUsuario().getLogin());
        assertEquals("user1", achievements.get(1).getUsuario().getLogin());
        Optional<Achievement> a1 = achievements.stream()
                .filter(a -> "POINTS_TYPE".equals(a.getNome())).findAny();
        Optional<Achievement> a2 = achievements.stream()
                .filter(a -> "BADGE_NAME".equals(a.getNome())).findAny();
        assertTrue(a1.isPresent());
        assertTrue(a2.isPresent());

        achievements = achievementRepository.findAllByLoginUsuario("user2");
        assertEquals(2, achievements.size());
        assertEquals("user2", achievements.get(0).getUsuario().getLogin());
        assertEquals("user2", achievements.get(1).getUsuario().getLogin());
        a1 = achievements.stream()
                .filter(a -> "POINTS_TYPE".equals(a.getNome())).findAny();
        a2 = achievements.stream()
                .filter(a -> "BADGE_NAME".equals(a.getNome())).findAny();
        assertTrue(a1.isPresent());
        assertTrue(a2.isPresent());
    }

    @Test
    void deveRecuperarUmAchievmentDeUmDeterminadoUsuarioPeloNome() {
        Optional<Achievement> achievement = achievementRepository.findByNomeToUsuario("POINTS_TYPE", "user1");
        assertTrue(achievement.isPresent());
        assertEquals(Points.class, achievement.get().getClass());
        Points points = (Points) achievement.get();
        assertEquals(100l, points.getQuantidadePontos());
    }

    @Test
    void deveCadastrarUmaNovaBadgeDeUmDeterminadoUsuario() {
        achievementRepository.saveAndFlush(new Badge("BADGE_NAME2", usuario1));

        List<Achievement> achievements = achievementRepository.findAllByLoginUsuario("user1");
        assertEquals(3, achievements.size());
        assertEquals("user1", achievements.get(0).getUsuario().getLogin());
        assertEquals("user1", achievements.get(1).getUsuario().getLogin());
        assertEquals("user1", achievements.get(2).getUsuario().getLogin());
        Optional<Achievement> a1 = achievements.stream()
                .filter(a -> "POINTS_TYPE".equals(a.getNome())).findAny();
        Optional<Achievement> a2 = achievements.stream()
                .filter(a -> "BADGE_NAME".equals(a.getNome())).findAny();
        Optional<Achievement> a3 = achievements.stream()
                .filter(a -> "BADGE_NAME2".equals(a.getNome())).findAny();
        assertTrue(a1.isPresent());
        assertTrue(a2.isPresent());
        assertTrue(a3.isPresent());
    }
}