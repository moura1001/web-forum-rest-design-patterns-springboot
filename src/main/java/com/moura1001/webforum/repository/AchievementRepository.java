package com.moura1001.webforum.repository;

import com.moura1001.webforum.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement, String> {
    @Query("SELECT a FROM Achievement a WHERE a.usuario.login = :login")
    List<Achievement> findAllByLoginUsuario(String login);

    @Query("SELECT a FROM Achievement a WHERE a.usuario.login = :login AND a.nome = :nome")
    Optional<Achievement> findByNomeToUsuario(String nome, String login);

    @Query("SELECT COUNT(a)>0 FROM Achievement a WHERE a.usuario.login = :login AND a.nome = :nome")
    boolean existsByNomeToUsuario(String nome, String login);
}
