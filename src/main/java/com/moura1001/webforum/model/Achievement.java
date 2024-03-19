package com.moura1001.webforum.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Achievement {

    @Id
    protected String nome;

    @Id
    @ManyToOne
    @JoinColumn(name = "login")
    protected Usuario usuario;

    protected Achievement(){
    }

    protected Achievement(String nome, Usuario usuario) {
        this.nome = nome;
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
