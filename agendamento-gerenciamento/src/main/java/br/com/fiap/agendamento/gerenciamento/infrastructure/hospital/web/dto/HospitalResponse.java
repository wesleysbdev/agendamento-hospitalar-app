package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

public record HospitalResponse(
        UUID uuid,
        String nome,
        String endereco,
        String telefone,
        boolean ativo,
        boolean excluido,
        DayOfWeek diaSemanaInicio,
        DayOfWeek diaSemanaFim,
        LocalTime horaInicio,
        LocalTime horaFim,
        Duration tempoLimiteCancelamento,
        Duration tempoToleranciaPosConsulta,
        Duration tempoMinimoConsulta
) {
}
