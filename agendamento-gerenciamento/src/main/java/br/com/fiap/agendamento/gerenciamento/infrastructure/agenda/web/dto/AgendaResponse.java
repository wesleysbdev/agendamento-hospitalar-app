package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record AgendaResponse(
        UUID uuid,
        UUID medicoUuid,
        String medicoNome,
        UUID hospitalUuid,
        String hospitalNome,
        List<LocalTime> horarios
) {
}
