package br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.AgendaDTO;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;

import java.util.List;
import java.util.UUID;

public interface GestaoConsultaAgenda {
    List<AgendaDTO> listarAgendas(UsuarioAutenticado usuarioAutenticado);

    AgendaDTO buscarAgendaPorUuid(UUID uuid, UsuarioAutenticado usuarioAutenticado);

    List<AgendaDTO> buscarAgendasPorMedico(UUID medicoUuid, UsuarioAutenticado usuarioAutenticado);

    List<AgendaDTO> buscarAgendasPorHospital(UUID hospitalUuid, UsuarioAutenticado usuarioAutenticado);
}
