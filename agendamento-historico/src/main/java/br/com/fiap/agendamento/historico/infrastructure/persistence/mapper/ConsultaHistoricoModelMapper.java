package br.com.fiap.agendamento.historico.infrastructure.persistence.mapper;

import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import br.com.fiap.agendamento.historico.domain.consulta.enums.StatusConsulta;
import br.com.fiap.agendamento.historico.domain.hospital.entity.HospitalHistorico;
import br.com.fiap.agendamento.historico.domain.usuario.entity.MedicoHistorico;
import br.com.fiap.agendamento.historico.domain.usuario.entity.PacienteHistorico;
import br.com.fiap.agendamento.historico.infrastructure.persistence.model.ConsultaHistoricoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConsultaHistoricoModelMapper {

    @Mapping(target = "paciente", expression = "java(mapPaciente(model))")
    @Mapping(target = "medico", expression = "java(mapMedico(model))")
    @Mapping(target = "hospital", expression = "java(mapHospital(model))")
    @Mapping(target = "status", expression = "java(mapStatus(model.getStatus()))")
    ConsultaHistorico paraEntidade(ConsultaHistoricoModel model);

    default PacienteHistorico mapPaciente(ConsultaHistoricoModel model) {
        return new PacienteHistorico(
            model.getPacienteId(),
            model.getPacienteNome(),
            model.getPacienteEmail(),
            model.getPacienteTelefone()
        );
    }

    default MedicoHistorico mapMedico(ConsultaHistoricoModel model) {
        return new MedicoHistorico(
            model.getMedicoId(),
            model.getMedicoNome(),
            model.getMedicoEmail()
        );
    }

    default HospitalHistorico mapHospital(ConsultaHistoricoModel model) {
        return new HospitalHistorico(
            model.getHospitalId(),
            model.getHospitalNome()
        );
    }

    default StatusConsulta mapStatus(String status) {
        if (status == null) {
            return StatusConsulta.AGENDADA;
        }
        try {
            return StatusConsulta.valueOf(status);
        } catch (IllegalArgumentException e) {
            return StatusConsulta.AGENDADA;
        }
    }
}
