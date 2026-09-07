package br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.consulta.AgendaDTO;

import java.util.List;
import java.util.UUID;

public interface GestaoConsultaAgenda {
    List<AgendaDTO> listarAgendas();

    AgendaDTO buscarAgendaPorUuid(UUID uuid);

    List<AgendaDTO> buscarAgendasPorMedico(UUID medicoUuid);

    List<AgendaDTO> buscarAgendasPorHospital(UUID hospitalUuid);
}
