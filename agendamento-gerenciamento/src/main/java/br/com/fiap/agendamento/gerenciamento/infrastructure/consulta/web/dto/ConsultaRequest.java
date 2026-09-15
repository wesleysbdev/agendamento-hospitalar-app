package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ConsultaRequest(
        @NotNull(message = "A identificação da agenda é obrigatório.")
        UUID agendaId,
        @NotNull(message = "A identificação do paciente é obrigatório.")
        UUID pacienteId,
        @NotNull(message = "A identificação do horário é obrigatório.")
        UUID horarioId,
        @NotNull(message = "A identificação da data é obrigatório.")
        LocalDate data
) {
}
