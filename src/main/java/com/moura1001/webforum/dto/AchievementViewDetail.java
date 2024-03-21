package com.moura1001.webforum.dto;

import com.moura1001.webforum.model.Usuario;

import java.util.List;

public record AchievementViewDetail(
        String login,
        String nome,
        List<AchievementView> achievements
) {
    public AchievementViewDetail(Usuario usuario, List<AchievementView> achievements) {
        this(
                usuario.getLogin(),
                usuario.getNome(),
                achievements
        );
    }
}
