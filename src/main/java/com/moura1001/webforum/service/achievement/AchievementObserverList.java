package com.moura1001.webforum.service.achievement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AchievementObserverList {

    @Autowired
    @Qualifier("observadorBadgeInventor")
    @Lazy
    private ObservadorBadgeInventor observador1;
    @Autowired
    @Qualifier("observadorBadgePartOfTheCommunity")
    @Lazy
    private ObservadorBadgePartOfTheCommunity observador2;
    private final List<AchievementObserver> OBSERVADORES = new ArrayList<>(2);

    public List<AchievementObserver> getOBSERVADORES() {
        if (OBSERVADORES.isEmpty())
            OBSERVADORES.addAll(List.of(
                    observador1,
                    observador2
            ));
        return OBSERVADORES;
    }
}
