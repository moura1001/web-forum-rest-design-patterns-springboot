package com.moura1001.webforum.service.storage;

import com.moura1001.webforum.model.Achievement;

import java.util.List;

public interface AchievementStorage {

    void adicionarAchievement(String usuario, Achievement a);

    List<Achievement> getAllAchievements(String usuario);

    Achievement getAchievement(String usuario, String nomeAchievement);

}
