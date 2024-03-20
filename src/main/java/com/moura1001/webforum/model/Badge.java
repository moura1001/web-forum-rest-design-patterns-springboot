package com.moura1001.webforum.model;

import jakarta.persistence.Entity;

@Entity
public class Badge extends Achievement {
    public Badge(){
    }

    public Badge(String nome, Usuario usuario) {
        super(nome, usuario);
    }

    @Override
    public void adicionar(Achievement a) {
    }
}
