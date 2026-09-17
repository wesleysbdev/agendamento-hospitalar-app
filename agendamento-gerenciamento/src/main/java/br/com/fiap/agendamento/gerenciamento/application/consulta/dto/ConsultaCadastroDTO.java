package br.com.fiap.agendamento.gerenciamento.application.consulta.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ConsultaCadastroDTO(
        UUID pacienteId,
        UUID agendaId,
        UUID horarioId,
        LocalDate data
) {
}
