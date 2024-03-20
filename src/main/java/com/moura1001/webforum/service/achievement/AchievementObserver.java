package com.moura1001.webforum.service.achievement;

import com.moura1001.webforum.model.Achievement;

public interface AchievementObserver {

    void achievementUpdate(String user, Achievement a);

    boolean deveAdicionarObservador(Achievement a);
}
