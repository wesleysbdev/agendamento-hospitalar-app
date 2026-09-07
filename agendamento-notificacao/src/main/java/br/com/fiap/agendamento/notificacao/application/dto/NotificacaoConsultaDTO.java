package br.com.fiap.agendamento.notificacao.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificacaoConsultaDTO(
        UUID consultaId,
        UUID pacienteId,
        LocalDateTime dataConsulta
) {
}
