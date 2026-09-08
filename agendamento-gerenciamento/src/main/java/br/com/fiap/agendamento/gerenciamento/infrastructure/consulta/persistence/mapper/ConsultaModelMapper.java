package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.persistence.model.ConsultaModel;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.mapper.HospitalModelMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.persistence.mapper.UsuarioModelMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UsuarioModelMapper.class, HospitalModelMapper.class})
public interface ConsultaModelMapper {

    @Mapping(source = "status", target = "estado")
    Consulta paraEntidade(ConsultaModel model);
}
