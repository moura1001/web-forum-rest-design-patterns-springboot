package com.moura1001.webforum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Points extends Achievement {
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long quantidadePontos;

    public Points(){
    }

    public Points(String nome, Usuario usuario) {
        super(nome, usuario);
        quantidadePontos = 0l;
    }

    public Points(String nome, Usuario usuario, Long quantidadePontos) {
        super(nome, usuario);
        this.quantidadePontos = quantidadePontos;
    }

    public Long getQuantidadePontos() {
        return quantidadePontos;
    }
}
