package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.edicao;

import jakarta.validation.constraints.NotBlank;

public record AlterarSenhaRequest(
        @NotBlank(message = "A nova senha é obrigatório.")
        String novaSenha
) {
}
