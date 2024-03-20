package com.moura1001.webforum.service.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AchievementStorageFactory {
    @Autowired
    @Qualifier("relationalDatabaseAchievementStorage")
    private AchievementStorage storage;

    public AchievementStorage getAchievementStorage() {
        return storage;
    }
}
