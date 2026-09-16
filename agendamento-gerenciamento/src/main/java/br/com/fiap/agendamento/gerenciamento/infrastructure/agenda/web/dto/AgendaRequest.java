package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record AgendaRequest(
        @NotNull(message = "O campo hospitalId é obrigatório.")
        UUID hospitalId,
        @NotEmpty(message = "O campo horarios deve possuir pelo menos um horário.")
        List<@Valid HorarioAgendaRequest> horarios
) {
}
