package com.moura1001.webforum.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AchievementPK implements Serializable {
    private String nome;
    private String login;

    public AchievementPK(){
    }

    public AchievementPK(String nome, String login) {
        this.nome = nome;
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AchievementPK that)) return false;
        return Objects.equals(nome, that.nome) && Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, login);
    }
}
