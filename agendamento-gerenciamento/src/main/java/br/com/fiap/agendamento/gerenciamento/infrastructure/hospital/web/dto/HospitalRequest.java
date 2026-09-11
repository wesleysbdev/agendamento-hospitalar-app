package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.DayOfWeek;
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
        @NotBlank(message = "O campo tempoLimiteCancelamentoMinutos é obrigatório.")
        @PositiveOrZero(message = "O tempo limite de cancelamento deve ser maior ou igual a zero.")
        long tempoLimiteCancelamentoMinutos,
        @NotBlank(message = "O campo tempoToleranciaPosConsultaMinutos é obrigatório.")
        @PositiveOrZero(message = "O tempo de tolerância para consulta deve ser maior ou igual a zero.")
        long tempoToleranciaPosConsultaMinutos,
        @NotBlank(message = "O campo tempoMinimoConsultaMinutos é obrigatório.")
        @Positive(message = "O tempo mínimo de duração de cada consulta deve ser maior que zero.")
        long tempoMinimoConsultaMinutos
) {
}

// mock

//{
//        "nome": "Hospital São João",
//        "endereco": "Rua das Flores, 100",
//        "telefone": "11999999999",
//        "diaSemanaInicio": "MONDAY",
//        "diaSemanaFim": "FRIDAY",
//        "horaInicio": "08:00:00",
//        "horaFim": "18:00:00",
//        "tempoLimiteCancelamentoMinutos": 120,
//        "tempoToleranciaPosConsultaMinutos": 15,
//        "tempoMinimoConsultaMinutos": 30
//        }
