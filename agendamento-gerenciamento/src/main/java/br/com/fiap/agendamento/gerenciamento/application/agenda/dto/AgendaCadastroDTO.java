package br.com.fiap.agendamento.gerenciamento.application.agenda.dto;

import java.util.List;
import java.util.UUID;

public record AgendaCadastroDTO(
        UUID hospitalId,
        List<HorarioAgendaCadastroDTO> horarios
) {
}
