package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MedicoRequest(
        @NotBlank(message = "O campo nome é obrigatório.")
        String nome,
        @NotBlank(message = "O campo nome é obrigatório.")
        @Email(message = "O email informado não corresponde a um formato válido.")
        String email,
        @NotBlank(message = "O campo senha é obrigatório.")
        String senha,
        @NotBlank(message = "O campo crm é obrigatório.")
        String crm
) {
}
