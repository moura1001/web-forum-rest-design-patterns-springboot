package com.moura1001.webforum.model;

import jakarta.persistence.Entity;

@Entity
public class Badge extends Achievement {
    public Badge(){
    }

    public Badge(String nome, Usuario usuario) {
        this.nome = nome;
        this.usuario = usuario;
    }
}
