package com.moura1001.webforum.service.storage;

import com.moura1001.webforum.model.Achievement;
import com.moura1001.webforum.repository.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("relationalDatabaseAchievementStorage")
public class RelationalDatabaseAchievementStorage implements AchievementStorage {
    @Autowired
    private AchievementRepository achievementRepository;

    @Override
    public void adicionarAchievement(String usuario, Achievement a) {
        Optional<Achievement> achievement =  achievementRepository.findByNomeToUsuario(a.getNome(), usuario);
        if (achievement.isEmpty())
            achievementRepository.save(a);
        else {
            Achievement updatedAchievement = achievement.get();
            updatedAchievement.adicionar(a);
            achievementRepository.save(updatedAchievement);
        }
    }

    @Override
    public List<Achievement> getAllAchievements(String usuario) {
        return achievementRepository.findAllByLoginUsuario(usuario);
    }

    @Override
    public Achievement getAchievement(String usuario, String nomeAchievement) {
        Optional<Achievement> a = achievementRepository.findByNomeToUsuario(nomeAchievement, usuario);
        if (a.isPresent())
            return a.get();

        return null;
    }
}
