package br.com.fiap.agendamento.gerenciamento.application.agenda.dto;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record AgendaCadastroDTO(
        UUID medicoUuid,
        UUID hospitalUuid,
        List<LocalTime> horarios
) {
}
