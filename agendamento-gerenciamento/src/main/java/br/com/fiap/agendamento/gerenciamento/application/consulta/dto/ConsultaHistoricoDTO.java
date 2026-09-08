package br.com.fiap.agendamento.gerenciamento.application.consulta.dto;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.enums.ConsultaEstado;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultaHistoricoDTO(
        UUID uuid,
        LocalDateTime horario,
        UUID medicoUuid,
        String medicoNome,
        UUID hospitalUuid,
        String hospitalNome,
        String hospitalEndereco,
        ConsultaEstado estado
) {
}
