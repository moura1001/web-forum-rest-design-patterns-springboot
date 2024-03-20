package com.moura1001.webforum.repository;

import com.moura1001.webforum.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @BeforeAll
    void init() {
        achievementRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
        List<Usuario> usuarios = List.of(
                new Usuario("user1", "Usuário 1"),
                new Usuario("user2", "Usuário 2")
        );
        usuarioRepository.saveAllAndFlush(usuarios);
    }

    @Test
    void deveRecuperarTodosOsUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAllByOrderByNomeAsc();
        assertEquals(2, usuarios.size());
        assertEquals("user1", usuarios.get(0).getLogin());
        assertEquals("user2", usuarios.get(1).getLogin());
    }

    @Test
    void deveRecuperarUmUsuarioPeloSeuLogin() {
        Usuario usuario = usuarioRepository.findByLogin("user1").orElseGet(null);
        assertNotNull(usuario);
        assertEquals("Usuário 1", usuario.getNome());
    }

    @Test
    void deveInserirUmNovoUsuario() {
        usuarioRepository.saveAndFlush(new Usuario("user3", "Usuário 3"));
        List<Usuario> usuarios = usuarioRepository.findAllByOrderByNomeAsc();
        assertEquals(3, usuarios.size());
        assertEquals("user1", usuarios.get(0).getLogin());
        assertEquals("user2", usuarios.get(1).getLogin());
        assertEquals("user3", usuarios.get(2).getLogin());
    }
}