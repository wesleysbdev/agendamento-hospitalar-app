package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto;

import jakarta.validation.constraints.NotBlank;

public record PacienteRequest(
        @NotBlank String nome,
        @NotBlank String email,
        @NotBlank String senha,
        @NotBlank String telefone
) {
}
