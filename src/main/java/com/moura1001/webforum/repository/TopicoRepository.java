package com.moura1001.webforum.repository;

import com.moura1001.webforum.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<Topico> findAllByOrderByIdDesc();

    @Query("SELECT t FROM Topico t WHERE t.usuario.login = :login ORDER BY t.id DESC")
    List<Topico> findAllByLoginUsuario(String login);
}
