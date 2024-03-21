package com.moura1001.webforum.service;

import com.moura1001.webforum.model.Usuario;
import com.moura1001.webforum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (RuntimeException e) {
            throw new RuntimeException("internal error: " + e.getMessage());
        }
    }

    public List<Usuario> obterUsuariosCadastrados() {
        try {
            return usuarioRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("internal error: " + e.getMessage());
        }
    }
}
