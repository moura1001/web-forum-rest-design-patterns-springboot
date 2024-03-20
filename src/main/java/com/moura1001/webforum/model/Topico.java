package com.moura1001.webforum.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "topicos")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topico_id")
    private Long id;

    @Column(nullable = false, length = 512)
    private String titulo;

    @Column(nullable = false, length = 1024)
    private String conteudo;

    //@ManyToOne(cascade = CascadeType.REMOVE)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "login")
    private Usuario usuario;

    public Topico() {
    }

    public Topico(String titulo, String conteudo, Usuario usuario) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.usuario = usuario;
    }

    public Topico(Long id, String titulo, String conteudo, Usuario usuario) {
        this(titulo, conteudo, usuario);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
}
