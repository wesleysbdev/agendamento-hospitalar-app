package br.com.fiap.agendamento.historico.domain.consulta.entity;


import br.com.fiap.agendamento.historico.domain.consulta.enums.StatusConsulta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record ConsultaHistorico(
        UUID id,
        UUID pacienteId,
        UUID agendaId,
        LocalDate data,
        LocalTime horario,
        StatusConsulta status,
        LocalDateTime criadoEm
) {
}
