package com.moura1001.webforum.controller;

import com.moura1001.webforum.dto.*;
import com.moura1001.webforum.model.Comentario;
import com.moura1001.webforum.model.Topico;
import com.moura1001.webforum.service.ForumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/forum/api/v1/topicos")
public class TopicoController {
    @Autowired
    @Qualifier("forumServiceGamificationProxy")
    private ForumService forumService;

    @PostMapping
    public ResponseEntity<TopicoViewCreate> salvarTopico(@RequestBody @Valid TopicoDTO topico) {
        Topico topicoSalvo = forumService.adicionarTopico(topico.login(), topico.titulo(), topico.conteudo());
        return ResponseEntity.created(
                URI.create("/forum/api/v1/topicos/"+topicoSalvo.getId())
        ).body(new TopicoViewCreate(topicoSalvo));
    }

    @GetMapping
    public ResponseEntity<List<TopicoViewCreate>> obterTopicos() {
        List<TopicoViewCreate> topicos = forumService.obterTopicosCadastrados().stream()
                .map((topico) -> new TopicoViewCreate(topico)).collect(Collectors.toList());
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoViewDetail> visualizarTopico(@PathVariable Long id) {
        Topico t = forumService.obterTopico(id);
        List<ComentarioView> comentarios = forumService.obterComentariosCadastrados(id).stream()
                .map((comentario) -> new ComentarioView(comentario)).collect(Collectors.toList());
        return ResponseEntity.ok(new TopicoViewDetail(t, comentarios));
    }

    @PostMapping("/{id}/adicionarComentario")
    public ResponseEntity<String> salvarComentario(
            @PathVariable Long id,
            @RequestBody @Valid ComentarioDTO comentario) {
        Comentario comentarioSalvo = forumService.adicionarComentario(comentario.login(), comentario.topicoId(), comentario.conteudo());
        return ResponseEntity.created(
                URI.create("/forum/api/v1/topicos/"+id+"/comentarios/"+comentarioSalvo.getId())
        ).body("comentário adicionado com sucesso");
    }
}
