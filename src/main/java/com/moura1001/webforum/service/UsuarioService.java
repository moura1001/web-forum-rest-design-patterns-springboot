package com.moura1001.webforum.service;

import com.moura1001.webforum.model.Achievement;
import com.moura1001.webforum.model.Usuario;
import com.moura1001.webforum.repository.UsuarioRepository;
import com.moura1001.webforum.service.storage.AchievementStorageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AchievementStorageFactory achievementStorageFactory;

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

    public Usuario obterUsuario(String login) {
        return usuarioRepository.findByLogin(login).orElseThrow(() -> {
            throw new RuntimeException("usuário não existe");
        });
    }

    public List<Achievement> obterAchievements(String login) {
        return achievementStorageFactory.getAchievementStorage().getAllAchievements(login);
    }
}
