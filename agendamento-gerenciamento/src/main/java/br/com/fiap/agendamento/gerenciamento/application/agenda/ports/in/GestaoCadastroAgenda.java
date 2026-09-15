package br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.AgendaCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.HorarioAgendaCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;

import java.util.List;
import java.util.UUID;

public interface GestaoCadastroAgenda {
    Agenda cadastrar(AgendaCadastroDTO agendaCadastroDTO, UsuarioAutenticado usuarioAutenticado);

    Agenda adicionarHorarios(UUID agendaUuid, List<HorarioAgendaCadastroDTO> horarios, UsuarioAutenticado usuarioAutenticado);

    Agenda removerHorario(UUID agendaUuid, UUID horarioUuid, UsuarioAutenticado usuarioAutenticado);
}
