package br.com.fiap.agendamento.gerenciamento.application.hospital.dto.edicao;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

public interface HospitalEdicaoDTO {
    UUID uuid();
    String nome();
    String endereco();
    Telefone telefone();
    DayOfWeek diaSemanaInicio();
    DayOfWeek diaSemanaFim();
    LocalTime horaInicio();
    LocalTime horaFim();
    Duration tempoLimiteCancelamento();
    Duration tempoToleranciaPosConsulta();
    Duration tempoMinimoConsulta();
}
