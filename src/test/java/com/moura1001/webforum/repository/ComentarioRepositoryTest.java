package com.moura1001.webforum.repository;

import com.moura1001.webforum.model.Comentario;
import com.moura1001.webforum.model.Topico;
import com.moura1001.webforum.model.Usuario;
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
class ComentarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    private static final Usuario usuario1 = new Usuario("user1", "Usuário 1");
    private static final Usuario usuario2 = new Usuario("user2", "Usuário 2");
    private static final Topico topico1 = new Topico(1l, "Tópico 1", "Conteúdo 1", usuario1);
    private static final Topico topico2 = new Topico(2l, "Tópico 2", "Conteúdo 2", usuario2);

    @BeforeAll
    void init() {
        List<Usuario> usuarios = List.of(usuario1, usuario2);
        usuarioRepository.saveAllAndFlush(usuarios);
        List<Topico> topicos = List.of(topico1, topico2);
        topicoRepository.saveAllAndFlush(topicos);
    }

    @AfterAll
    void finish() {
        comentarioRepository.deleteAll();
        topicoRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        comentarioRepository.deleteAll();
        List<Comentario> comentarios = List.of(
                new Comentario("Comentário 1", usuario1, topico2),
                new Comentario("Comentário 2", usuario2, topico1)
        );
        comentarioRepository.saveAllAndFlush(comentarios);
    }

    @Test
    public void deveListarTodosOsComentariosDeUmDeterminadoTopico() {
        List<Comentario> comentarios = comentarioRepository.findAllByTopicoId(2l);
        assertEquals(1, comentarios.size());
        assertEquals("user1", comentarios.get(0).getUsuario().getLogin());

        comentarios = comentarioRepository.findAllByTopicoId(1l);
        assertEquals(1, comentarios.size());
        assertEquals("user2", comentarios.get(0).getUsuario().getLogin());
    }

    @Test
    void deveCadastrarUmNovoComentarioNumDeterminadoTopico() {
        comentarioRepository.saveAndFlush(new Comentario("Comentário 3", usuario2, topico2));
        List<Comentario> comentarios = comentarioRepository.findAllByTopicoId(2l);
        assertEquals(2, comentarios.size());
        assertEquals("user2", comentarios.get(0).getUsuario().getLogin());
        assertEquals("Tópico 2", comentarios.get(0).getTopico().getTitulo());
        assertEquals("Comentário 3", comentarios.get(0).getConteudo());
        assertEquals("user1", comentarios.get(1).getUsuario().getLogin());
        assertEquals("Tópico 2", comentarios.get(1).getTopico().getTitulo());
        assertEquals("Comentário 1", comentarios.get(1).getConteudo());
    }
}