package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.edicao;

import jakarta.validation.constraints.NotBlank;

public record AlterarEstadoRequest(
        @NotBlank(message = "O novo estado é obrigatório.")
        boolean ativo
) {
}
