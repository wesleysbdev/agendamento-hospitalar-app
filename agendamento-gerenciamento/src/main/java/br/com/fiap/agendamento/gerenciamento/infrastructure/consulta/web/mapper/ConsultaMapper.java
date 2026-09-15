package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaDTO;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.mapper.ValueObjectMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.dto.ConsultaRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.dto.ConsultaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Mapper(componentModel = "spring", uses = ValueObjectMapper.class)
public interface ConsultaMapper {

    ConsultaCadastroDTO paraDTO(ConsultaRequest consultaRequest);

    @Mapping(target = "horario", expression = "java(combinarDataHorario(consulta.getData(), consulta.getHorario()))")
    @Mapping(target = "medicoNome", source = "agenda.medico.nome")
    @Mapping(target = "hospitalNome", source = "agenda.hospital.nome")
    @Mapping(target = "hospitalEndereco", source = "agenda.hospital.endereco")
    ConsultaResponse paraResponse(Consulta consulta);

    ConsultaResponse paraResponse(ConsultaDTO consulta);

    default LocalDateTime combinarDataHorario(LocalDate data, LocalTime horario) {
        if (data == null || horario == null) {
            return null;
        }
        return LocalDateTime.of(data, horario);
    }
}
