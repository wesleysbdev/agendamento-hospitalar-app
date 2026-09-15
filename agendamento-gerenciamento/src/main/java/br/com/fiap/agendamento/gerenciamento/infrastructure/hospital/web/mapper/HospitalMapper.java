package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.mapper;

import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.HospitalCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.HospitalDTO;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.mapper.ValueObjectMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Duration;

@Mapper(componentModel = "spring", uses = ValueObjectMapper.class)
public interface HospitalMapper {

    @Mapping(
            target = "tempoLimiteCancelamento",
            source = "tempoLimiteCancelamentoMinutos"
    )
    @Mapping(
            target = "tempoToleranciaPosConsulta",
            source = "tempoToleranciaPosConsultaMinutos"
    )
    @Mapping(
            target = "tempoMinimoConsulta",
            source = "tempoMinimoConsultaMinutos"
    )
    HospitalCadastroDTO paraDTO(HospitalRequest request);

    HospitalResponse paraResponse(Hospital hospital);

    HospitalResponse paraResponse(HospitalDTO hospital);

    default Duration map(Integer minutos) {
        return minutos == null
                ? null
                : Duration.ofMinutes(minutos);
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
