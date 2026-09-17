package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.edicao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EnfermeiroEdicaoRequest(
        @NotBlank(message = "O campo nome é obrigatório.")
        String nome,
        @NotBlank(message = "O campo nome é obrigatório.")
        @Email(message = "O email informado não corresponde a um formato válido.")
        String email
) {
}
