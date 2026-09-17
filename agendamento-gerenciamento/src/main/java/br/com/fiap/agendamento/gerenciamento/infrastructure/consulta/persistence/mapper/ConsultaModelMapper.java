package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.persistence.mapper.AgendaModelMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.mapper.ValueObjectMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.model.ConsultaModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.mapper.UsuarioModelMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        uses = {
                ValueObjectMapper.class,
                AgendaModelMapper.class,
                UsuarioModelMapper.class
        }
)
public interface ConsultaModelMapper {

    Consulta paraEntidade(ConsultaModel consulta);

    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "agenda", ignore = true)
    ConsultaModel paraModelo(Consulta consulta);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "agenda", ignore = true)
    void atualizarModelo(
            Consulta consulta,
            @MappingTarget ConsultaModel existente
    );
}
