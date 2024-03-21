package com.moura1001.webforum.controller;

import com.moura1001.webforum.dto.AchievementView;
import com.moura1001.webforum.dto.AchievementViewDetail;
import com.moura1001.webforum.model.Usuario;
import com.moura1001.webforum.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/forum/api/v1/achievements")
public class AchievementController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{login}")
    public ResponseEntity<AchievementViewDetail> obterAchievements(@PathVariable String login) {
        Usuario u = usuarioService.obterUsuario(login);
        List<AchievementView> achievements = usuarioService.obterAchievements(login).stream()
                .map((achievement) -> new AchievementView(achievement)).collect(Collectors.toList());
        return ResponseEntity.ok(new AchievementViewDetail(u, achievements));
    }
}
