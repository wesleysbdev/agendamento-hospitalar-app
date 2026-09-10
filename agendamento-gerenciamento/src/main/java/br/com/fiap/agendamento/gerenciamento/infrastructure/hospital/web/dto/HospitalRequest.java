package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public record HospitalRequest(
        @NotBlank(message = "O campo nome é obrigatório.")
        String nome,
        @NotBlank(message = "O campo endereco é obrigatório.")
        String endereco,
        @NotBlank(message = "O campo telefone é obrigatório.")
        String telefone,
        @NotBlank(message = "O campo diaSemanaInicio é obrigatório.")
        DayOfWeek diaSemanaInicio,
        @NotBlank(message = "O campo diaSemanaFim é obrigatório.")
        DayOfWeek diaSemanaFim,
        @NotBlank(message = "O campo horaInicio é obrigatório.")
        LocalTime horaInicio,
        @NotBlank(message = "O campo horaFim é obrigatório.")
        LocalTime horaFim,
        @NotBlank(message = "O campo tempoLimiteCancelamento é obrigatório.")
        Duration tempoLimiteCancelamento,
        @NotBlank(message = "O campo tempoToleranciaPosConsulta é obrigatório.")
        Duration tempoToleranciaPosConsulta,
        @NotBlank(message = "O campo tempoMinimoConsulta é obrigatório.")
        Duration tempoMinimoConsulta
) {
}
