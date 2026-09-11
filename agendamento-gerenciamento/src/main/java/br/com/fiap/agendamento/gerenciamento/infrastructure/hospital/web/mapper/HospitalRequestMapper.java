package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.cadastro.HospitalCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.edicao.HospitalEdicaoDTO;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalEdicaoRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalResponse;
import org.mapstruct.Mapper;

import java.time.Duration;

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

    default Duration map(long durationInMinutes) {
//        P = Period
//        T = Time
//        30M = 30 minutos
        return Duration.ofMinutes(durationInMinutes); // "PT30M"
    }
}


//mock
//
//{
//    "nome": "Hospital São João",
//        "endereco": "Rua das Flores, 100",
//        "telefone": "11999999999",
//        "ativo": true,
//        "excluido": false,
//        "diaSemanaInicio": "MONDAY",
//        "diaSemanaFim": "FRIDAY",
//        "horaInicio": "08:00:00",
//        "horaFim": "18:00:00",
//        "tempoLimiteCancelamento": "PT2H",
//        "tempoToleranciaPosConsulta": "PT15M",
//        "tempoMinimoConsulta": "PT30M"
//}
