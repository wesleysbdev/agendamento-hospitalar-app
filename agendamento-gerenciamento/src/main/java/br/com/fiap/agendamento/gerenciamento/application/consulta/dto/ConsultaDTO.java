package br.com.fiap.agendamento.gerenciamento.application.consulta.dto;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.enums.StatusConsulta;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultaDTO(
        UUID id,
        LocalDateTime horario,
        String medicoNome,
        String hospitalNome,
        String hospitalEndereco,
        StatusConsulta status
) {
}
