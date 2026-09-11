package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.model.HospitalModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Duration;

@Mapper(componentModel = "spring")
public interface HospitalModelMapper {

    Hospital paraEntidade(HospitalModel model);

    HospitalModel paraModelo(Hospital entidade);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    void atualizarModelo(
            Hospital hospital,
            @MappingTarget HospitalModel existente
    );

    default Telefone paraTelefone(String valor) {
        return valor == null ? null : new Telefone(valor);
    }

    default String paraString(Telefone telefone) {
        return telefone == null ? null : telefone.valor();
    }

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
