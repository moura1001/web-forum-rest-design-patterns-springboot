package com.moura1001.webforum.dto;

import com.moura1001.webforum.model.Usuario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(
        @NotEmpty @Size(min = 3, max = 32) String login,
        @NotEmpty @Size(min = 3, max = 512) String nome
) {
    public Usuario toEntity() {
        return new Usuario(
                this.login,
                this.nome
        );
    }
}
