package com.moura1001.webforum.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComentarioDTO(
        @NotEmpty @Size(min = 3, max = 32) String login,
        @NotNull Long topicoId,
        @NotEmpty @Size(min = 8, max = 1024) String conteudo
) {}
