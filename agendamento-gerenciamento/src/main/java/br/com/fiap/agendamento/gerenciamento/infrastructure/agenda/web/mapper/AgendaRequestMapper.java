package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.AgendaCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto.AgendaRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto.AgendaResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgendaRequestMapper {

//    AgendaCadastroDTO paraDTO(AgendaRequest request);
//
//    AgendaResponse paraResponse(Agenda agenda);
}
