package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.dto;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.enums.ConsultaEstado;

import java.time.LocalTime;

public record ConsultaResponse(
        LocalTime horario,
        String medicoNome,
        String hospitalNome,
        String hospitalEndereco,
        ConsultaEstado estado
) {
}
