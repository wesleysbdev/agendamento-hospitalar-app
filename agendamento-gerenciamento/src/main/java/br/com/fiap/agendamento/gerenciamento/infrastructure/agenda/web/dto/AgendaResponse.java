package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto;

import java.util.List;
import java.util.UUID;

public record AgendaResponse(
        UUID id,
        UUID medicoId,
        String medicoNome,
        UUID hospitalId,
        String hospitalNome,
        List<HorarioAgendaResponse> horarios
) {
}
