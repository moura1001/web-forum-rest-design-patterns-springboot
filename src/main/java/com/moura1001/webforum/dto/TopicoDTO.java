package com.moura1001.webforum.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record TopicoDTO(
        @NotEmpty @Size(min = 3, max = 32) String login,
        @NotEmpty @Size(min = 3, max = 512) String titulo,
        @NotEmpty @Size(min = 8, max = 1024) String conteudo
) {}
