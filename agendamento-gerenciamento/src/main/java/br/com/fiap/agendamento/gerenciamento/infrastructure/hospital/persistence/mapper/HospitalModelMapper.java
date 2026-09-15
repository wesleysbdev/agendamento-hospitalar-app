package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.mapper.ValueObjectMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.model.HospitalModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Duration;

@Mapper(componentModel = "spring", uses = ValueObjectMapper.class)
public interface HospitalModelMapper {


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
    Hospital paraEntidade(HospitalModel model);

    @Mapping(
            source = "tempoLimiteCancelamento",
            target = "tempoLimiteCancelamentoMinutos"
    )
    @Mapping(
            source = "tempoToleranciaPosConsulta",
            target = "tempoToleranciaPosConsultaMinutos"
    )
    @Mapping(
            source = "tempoMinimoConsulta",
            target = "tempoMinimoConsultaMinutos"
    )
    HospitalModel paraModelo(Hospital entidade);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    void atualizarModelo(Hospital hospital, @MappingTarget HospitalModel existente);

    default Duration paraDuration(Integer minutos) {
        return minutos == null
                ? null
                : Duration.ofMinutes(minutos);
    }

    default Integer paraMinutos(Duration duration) {
        return duration == null
                ? null
                : Math.toIntExact(duration.toMinutes());
    }
}
