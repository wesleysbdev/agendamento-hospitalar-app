package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

public record HospitalEdicaoRequest(
        @NotBlank(message = "O campo nome é obrigatório.")
        String nome,
        @NotBlank(message = "O campo endereco é obrigatório.")
        String endereco,
        @NotBlank(message = "O campo telefone é obrigatório.")
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
