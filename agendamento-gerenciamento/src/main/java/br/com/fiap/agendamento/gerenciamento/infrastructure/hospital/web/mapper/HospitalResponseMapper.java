package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.consulta.HospitalDTO;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HospitalResponseMapper {

    HospitalResponse paraResponse(HospitalDTO hospital);

    default String map(Telefone telefone) {
        return telefone == null ? null : telefone.valor();
    }
}
