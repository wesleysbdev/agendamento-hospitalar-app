package br.com.fiap.agendamento.gerenciamento.application.agenda.dto;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record AgendaDTO (
    UUID uuid,
    UUID medicoUuid,
    String medicoNome,
    UUID hospitalUuid,
    String hospitalNome,
    List<HorarioAgendaDTO> horarios
){}
