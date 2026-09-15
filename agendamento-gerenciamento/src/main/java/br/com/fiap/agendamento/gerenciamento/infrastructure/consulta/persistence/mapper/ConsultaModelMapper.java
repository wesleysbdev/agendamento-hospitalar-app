package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.mapper.ValueObjectMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.model.ConsultaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ValueObjectMapper.class)
public interface ConsultaModelMapper {

    Consulta paraEntidade(ConsultaModel consulta);

    ConsultaModel paraModelo(Consulta consulta);

    @Mapping(target = "id", ignore = true)
    void atualizarModelo(Consulta consulta, @MappingTarget ConsultaModel existente);
}
