package br.com.fiap.agendamento.gerenciamento.application.agenda.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public record HorarioAgendaDTO(
        UUID id,
        DayOfWeek diaSemana,
        LocalTime horario
) {
}
