package com.moura1001.webforum.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comentario_id")
    private Long id;

    @Column(nullable = false, length = 2048)
    private String conteudo;

    @ManyToOne
    @JoinColumn(name = "login")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Comentario() {
    }

    public Comentario(String conteudo, Usuario usuario, Topico topico) {
        this.conteudo = conteudo;
        this.usuario = usuario;
        this.topico = topico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Topico getTopico() {
        return topico;
    }

    public void setTopico(Topico topico) {
        this.topico = topico;
    }
}