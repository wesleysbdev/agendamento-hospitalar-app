package br.com.fiap.agendamento.gerenciamento.application.consulta.dto;

import br.com.fiap.agendamento.gerenciamento.application.consulta.enums.ConsultaEventType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultaEvent(
        ConsultaEventType eventType,
        UUID consultaId,
        UUID pacienteId,
        LocalDateTime dataConsulta
) {
}
