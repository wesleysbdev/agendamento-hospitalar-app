package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.dto;

import jakarta.validation.constraints.NotBlank;

public record ConsultaRequest(
        @NotBlank(message = "A identificação da agenda é obrigatório.")
        String agendaUuid,
        @NotBlank(message = "A identificação do paciente é obrigatório.")
        String pacienteUuid
) {
}
