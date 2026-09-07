package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.consulta.AgendaDTO;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto.AgendaResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgendaResponseMapper {

    AgendaResponse paraResponse(AgendaDTO agenda);
}
