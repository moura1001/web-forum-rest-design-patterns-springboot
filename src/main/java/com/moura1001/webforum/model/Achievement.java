package com.moura1001.webforum.model;

import com.moura1001.webforum.service.achievement.AchievementObserver;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@IdClass(AchievementPK.class)
public abstract class Achievement {

    @Id
    protected String nome;

    @Id
    @Column(name = "login_usuario", insertable = false, updatable = false)
    protected String login;

    //@ManyToOne(cascade = CascadeType.REMOVE)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "login")
    protected Usuario usuario;

    @Transient
    protected AchievementObserver observador;

    protected Achievement(){
    }

    protected Achievement(String nome, Usuario usuario) {
        this.nome = nome;
        this.usuario = usuario;
        this.login = usuario.getLogin();
    }

    public abstract void adicionar(Achievement a);

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void adicionarObservador(AchievementObserver observador) {
        if (observador == null)
            return;

        if (observador.deveAdicionarObservador(this)) {

            this.observador = observador;
        }
    }

    public void removerObservador() {
        observador = null;
    }
}
