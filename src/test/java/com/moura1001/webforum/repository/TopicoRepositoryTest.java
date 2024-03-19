package com.moura1001.webforum.repository;

import com.moura1001.webforum.model.Topico;
import com.moura1001.webforum.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TopicoRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    private static final Usuario usuario1 = new Usuario("user1", "Usuário 1");
    private static final Usuario usuario2 = new Usuario("user2", "Usuário 2");

    @BeforeAll
    void init() {
        List<Usuario> usuarios = List.of(usuario1, usuario2);
        usuarioRepository.saveAllAndFlush(usuarios);
    }

    @AfterAll
    void finish() {
        topicoRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        topicoRepository.deleteAll();
        List<Topico> topicos = List.of(
                new Topico("Tópico 1", "Conteúdo 1", usuario1),
                new Topico("Tópico 2", "Conteúdo 2", usuario2)
        );
        topicoRepository.saveAllAndFlush(topicos);
    }

    @Test
    public void deveListarTodosOsTopicosCadastrados() {
        List<Topico> topicos = topicoRepository.findAllByOrderByIdDesc();
        assertEquals(2, topicos.size());
        assertEquals("user2", topicos.get(0).getUsuario().getLogin());
        assertEquals("user1", topicos.get(1).getUsuario().getLogin());
    }

    @Test
    void deveRecuperarUmTopicoDeUmDeterminadoUsuarioPeloTitulo() {
        List<Topico> topicos = topicoRepository.findAllByOrderByIdDesc();
        Optional<Topico> topico = topicoRepository.findById(topicos.get(0).getId());
        assertTrue(topico.isPresent());
        assertEquals("Conteúdo 2", topico.get().getConteudo());
    }

    @Test
    void deveCadastrarUmNovoTopicoDeUmDeterminadoUsuario() {
        topicoRepository.saveAndFlush(new Topico("Tópico 3", "Conteúdo 3", usuario1));
        List<Topico> topicos = topicoRepository.findAllByLoginUsuario("user1");
        assertEquals(2, topicos.size());
        assertEquals("user1", topicos.get(0).getUsuario().getLogin());
        assertEquals("Tópico 3", topicos.get(0).getTitulo());
        assertEquals("user1", topicos.get(0).getUsuario().getLogin());
        assertEquals("Tópico 1", topicos.get(1).getTitulo());
    }
}