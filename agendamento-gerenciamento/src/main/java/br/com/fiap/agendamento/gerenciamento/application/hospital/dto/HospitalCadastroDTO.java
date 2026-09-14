package br.com.fiap.agendamento.gerenciamento.application.hospital.dto;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public record HospitalCadastroDTO(
        String nome,
        String endereco,
        String telefone,
        DayOfWeek diaSemanaInicio,
        DayOfWeek diaSemanaFim,
        LocalTime horaInicio,
        LocalTime horaFim,
        Duration tempoLimiteCancelamento,
        Duration tempoToleranciaPosConsulta,
        Duration tempoMinimoConsulta
) {
}
