package com.moura1001.webforum.dto;

import com.moura1001.webforum.model.Achievement;
import com.moura1001.webforum.model.Points;

public record AchievementView(
        String nome,
        Long quantidadePontos
) {
    public AchievementView(Achievement achievement) {
        this(
                achievement.getNome(),
                AchievementView.obterQuantidadePontos(achievement)
        );
    }

    private static Long obterQuantidadePontos(Achievement achievement) {
        Long quantidadePontos = 0l;
        if (achievement instanceof Points p)
            quantidadePontos = p.getQuantidadePontos();

        return quantidadePontos;
    }
}
