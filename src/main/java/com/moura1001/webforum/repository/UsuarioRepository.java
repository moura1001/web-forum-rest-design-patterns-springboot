package com.moura1001.webforum.repository;

import com.moura1001.webforum.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    List<Usuario> findAllByOrderByNomeAsc();

    Optional<Usuario> findByLogin(String login);
}
