package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.HorarioAgenda;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model.HorarioAgendaModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HorarioAgendaModelMapper {

    HorarioAgenda paraEntidade(HorarioAgendaModel model);

    HorarioAgendaModel paraModelo(HorarioAgenda entidade);

}