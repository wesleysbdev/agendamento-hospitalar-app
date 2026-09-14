package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Medico;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model.AgendaModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.mapper.ValueObjectMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.model.MedicoModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {ValueObjectMapper.class, HorarioAgendaModelMapper.class})
public interface AgendaModelMapper {

    Agenda paraEntidade(AgendaModel model);

    default Optional<Agenda> paraEntidade(Optional<AgendaModel> modelOpt) {
        return modelOpt.map(this::paraEntidade);
    }

    AgendaModel paraModelo(Agenda entidade);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    void atualizarModelo(Agenda agenda, @MappingTarget AgendaModel existente);

    MedicoModel paraModelo(Medico medico);

    Medico paraEntidade(MedicoModel model);

    @AfterMapping
    default void configurarRelacionamento(@MappingTarget AgendaModel agendaModel) {
        agendaModel.getHorarios()
                .forEach(horario -> horario.setAgenda(agendaModel));
    }

}
