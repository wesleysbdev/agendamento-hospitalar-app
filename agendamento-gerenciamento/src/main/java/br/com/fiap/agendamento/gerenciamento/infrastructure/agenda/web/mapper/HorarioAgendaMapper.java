package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.HorarioAgendaCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.HorarioAgendaDTO;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.HorarioAgenda;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto.HorarioAgendaRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto.HorarioAgendaResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HorarioAgendaMapper {
    HorarioAgendaCadastroDTO paraDTO(HorarioAgendaRequest horarioAgendaRequest);

    List<HorarioAgendaCadastroDTO> paraDTO(List<HorarioAgendaRequest> horarioAgendaRequest);

    HorarioAgendaResponse paraResponse(HorarioAgendaDTO horarioAgendaDTO);

    HorarioAgendaResponse paraResponse(HorarioAgenda horarioAgenda);
}
