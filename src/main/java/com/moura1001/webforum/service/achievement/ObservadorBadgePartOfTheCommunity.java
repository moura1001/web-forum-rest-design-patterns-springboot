package com.moura1001.webforum.service.achievement;

import com.moura1001.webforum.model.Achievement;
import com.moura1001.webforum.model.Badge;
import com.moura1001.webforum.model.Points;
import com.moura1001.webforum.service.storage.AchievementStorage;
import com.moura1001.webforum.service.storage.AchievementStorageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("observadorBadgePartOfTheCommunity")
public class ObservadorBadgePartOfTheCommunity implements AchievementObserver {

    @Autowired
    private AchievementStorageFactory achievementStorageFactory;

    @Override
    public void achievementUpdate(String user, Achievement a) {
        Points achievement = (Points) a;
        if (achievement.getQuantidadePontos() >= 100) {
            AchievementStorage storage = achievementStorageFactory.getAchievementStorage();
            storage.adicionarAchievement(a.getLogin(), new Badge("PART OF THE COMMUNITY", a.getUsuario()));
            a.removerObservador();
        }
    }

    @Override
    public boolean deveAdicionarObservador(Achievement a) {
        if (a instanceof Points && "PARTICIPATION".equals(a.getNome()))
            return true;

        return false;
    }
}
