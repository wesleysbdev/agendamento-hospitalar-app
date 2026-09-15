package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.AgendaCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.AgendaDTO;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto.AgendaRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto.AgendaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgendaMapper {

    AgendaCadastroDTO paraDTO(AgendaRequest request);

    @Mapping(source = "medico.uuid", target = "medicoUuid")
    @Mapping(source = "medico.nome", target = "medicoNome")
    @Mapping(source = "hospital.uuid", target = "hospitalUuid")
    @Mapping(source = "hospital.nome", target = "hospitalNome")
    AgendaResponse paraResponse(Agenda agenda);

    AgendaResponse paraResponse(AgendaDTO agenda);
}
