package br.com.fiap.agendamento.gerenciamento.application.agenda.dto;

import java.util.List;
import java.util.UUID;

public record AgendaDTO (
    UUID id,
    UUID medicoId,
    String medicoNome,
    UUID hospitalId,
    String hospitalNome,
    List<HorarioAgendaDTO> horarios
){}
