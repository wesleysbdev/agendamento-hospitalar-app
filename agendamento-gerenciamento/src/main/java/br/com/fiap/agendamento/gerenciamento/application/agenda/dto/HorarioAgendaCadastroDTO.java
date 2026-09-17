package br.com.fiap.agendamento.gerenciamento.application.agenda.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record HorarioAgendaCadastroDTO(
        DayOfWeek diaSemana,
        LocalTime horario
) {
}
