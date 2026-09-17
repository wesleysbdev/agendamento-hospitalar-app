package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.mapper.DurationMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.mapper.ValueObjectMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.model.HospitalModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        uses = {
                ValueObjectMapper.class,
                DurationMapper.class
        }
)
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
            target = "tempoLimiteCancelamentoMinutos",
            source = "tempoLimiteCancelamento"
    )
    @Mapping(
            target = "tempoToleranciaPosConsultaMinutos",
            source = "tempoToleranciaPosConsulta"
    )
    @Mapping(
            target = "tempoMinimoConsultaMinutos",
            source = "tempoMinimoConsulta"
    )
    HospitalModel paraModelo(Hospital entidade);

    @Mapping(target = "id", ignore = true)
    @Mapping(
            target = "tempoLimiteCancelamentoMinutos",
            source = "tempoLimiteCancelamento"
    )
    @Mapping(
            target = "tempoToleranciaPosConsultaMinutos",
            source = "tempoToleranciaPosConsulta"
    )
    @Mapping(
            target = "tempoMinimoConsultaMinutos",
            source = "tempoMinimoConsulta"
    )
    void atualizarModelo(
            Hospital hospital,
            @MappingTarget HospitalModel existente
    );
}
