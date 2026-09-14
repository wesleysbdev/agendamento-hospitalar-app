package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Medico;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model.AgendaModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AgendaModelMapper {

//    Agenda paraEntidade(AgendaModel model);
//
//    AgendaModel paraModelo(Agenda entidade);
//
//    void atualizarModelo(Agenda agenda, @MappingTarget AgendaModel existente);
}
