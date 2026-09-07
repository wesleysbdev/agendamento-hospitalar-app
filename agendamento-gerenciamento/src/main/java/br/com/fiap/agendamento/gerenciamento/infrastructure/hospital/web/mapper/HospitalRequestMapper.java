package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.cadastro.HospitalCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.edicao.HospitalEdicaoDTO;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalEdicaoRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HospitalRequestMapper {

    HospitalCadastroDTO paraDTO(HospitalRequest request);

    HospitalEdicaoDTO paraDTO(HospitalEdicaoRequest request);

    HospitalResponse paraResponse(Hospital hospital);

    default Telefone map(String telefone) {
        return telefone == null ? null : new Telefone(telefone);
    }

    default String map(Telefone telefone) {
        return telefone == null ? null : telefone.valor();
    }
}
