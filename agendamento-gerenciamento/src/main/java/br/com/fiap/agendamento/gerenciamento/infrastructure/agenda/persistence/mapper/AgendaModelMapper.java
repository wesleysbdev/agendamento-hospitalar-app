package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.model.AgendaModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.mapper.ValueObjectMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.mapper.HospitalModelMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.mapper.UsuarioModelMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper(componentModel = "spring",
        uses = {
                ValueObjectMapper.class,
                HorarioAgendaModelMapper.class,
                UsuarioModelMapper.class,
                HospitalModelMapper.class
        }
)
public interface AgendaModelMapper {

    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "hospital", ignore = true)
    AgendaModel paraModelo(Agenda entidade);

    Agenda paraEntidade(AgendaModel model);

    default Optional<Agenda> paraEntidade(Optional<AgendaModel> modelOpt) {
        return modelOpt.map(this::paraEntidade);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "hospital", ignore = true)
    void atualizarModelo(
            Agenda agenda,
            @MappingTarget AgendaModel existente
    );

    @AfterMapping
    default void configurarRelacionamento(
            @MappingTarget AgendaModel agendaModel
    ) {
        agendaModel.getHorarios()
                .forEach(horario -> horario.setAgenda(agendaModel));
    }

}
