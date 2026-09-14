package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record AgendaRequest(
        @NotNull(message = "O campo medicoUuid é obrigatório.")
        UUID medicoUuid,
        @NotNull(message = "O campo hospitalUuid é obrigatório.")
        UUID hospitalUuid,
        @NotNull(message = "O campo horarios são obrigatórios.")
        List<HorarioAgendaRequest> horarios
) {
}
