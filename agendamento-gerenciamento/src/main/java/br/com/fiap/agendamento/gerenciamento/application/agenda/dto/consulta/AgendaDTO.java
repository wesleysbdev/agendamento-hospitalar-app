package br.com.fiap.agendamento.gerenciamento.application.agenda.dto.consulta;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AgendaDTO {
    UUID uuid();
    UUID medicoUuid();
    String medicoNome();
    UUID hospitalUuid();
    String hospitalNome();
    List<LocalTime> horarios();
}
