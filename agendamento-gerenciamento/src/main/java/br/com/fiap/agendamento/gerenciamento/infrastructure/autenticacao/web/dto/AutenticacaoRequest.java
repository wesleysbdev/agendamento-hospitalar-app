package br.com.fiap.agendamento.gerenciamento.infrastructure.autenticacao.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AutenticacaoRequest(
        @NotBlank(message = "O login é obrigatório")
        @Email(message = "O login deve ser um e-mail válido")
        String email,
        @NotBlank(message = "A senha é obrigatória")
        String senha
) {
}
