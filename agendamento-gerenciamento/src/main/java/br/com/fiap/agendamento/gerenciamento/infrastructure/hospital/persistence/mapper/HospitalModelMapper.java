package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.mapper;

import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.model.HospitalModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.Duration;

@Mapper(componentModel = "spring")
public interface HospitalModelMapper {

    default Telefone paraTelefone(String valor) {
        return valor == null ? null : new Telefone(valor);
    }

    default String paraString(Telefone telefone) {
        return telefone == null ? null : telefone.valor();
    }

    default Duration paraDuration(Integer minutos) {
        return minutos == null ? null : Duration.ofMinutes(minutos);
    }

    default Integer paraMinutos(Duration duration) {
        return duration == null ? null : (int) duration.toMinutes();
    }

    Hospital paraEntidade(HospitalModel model);

    HospitalModel paraModelo(Hospital entidade);

    void atualizarModelo(Hospital hospital, @MappingTarget HospitalModel existente);
}
