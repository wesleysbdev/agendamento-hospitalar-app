package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto;

import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record HorarioAgendaRequest(
        @NotNull(message = "O campo diaSemana é obrigatório.")
        DayOfWeek diaSemana,
        @NotNull(message = "O campo horario é obrigatório.")
        LocalTime horario
) {
}
