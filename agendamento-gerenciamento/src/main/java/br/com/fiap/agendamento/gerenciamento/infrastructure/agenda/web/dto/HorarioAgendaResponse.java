package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public record HorarioAgendaResponse(
        UUID uuid,
        DayOfWeek diaSemana,
        LocalTime horario
) {
}
