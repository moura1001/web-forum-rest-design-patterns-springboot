package com.moura1001.webforum.repository;

import com.moura1001.webforum.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    @Query("SELECT c FROM Comentario c WHERE c.topico.id = :id ORDER BY c.id DESC")
    List<Comentario> findAllByTopicoId(Long id);
}
