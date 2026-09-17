package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AdicionarHorariosRequest(
        @NotEmpty(message = "É necessário informar pelo menos um horário.")
        List<@Valid HorarioAgendaRequest> horarios
) {
}
