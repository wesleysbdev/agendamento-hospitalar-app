package br.com.fiap.agendamento.historico.infrastructure.graphql.mapper;

import br.com.fiap.agendamento.historico.domain.consulta.entity.ConsultaHistorico;
import br.com.fiap.agendamento.historico.domain.consulta.enums.StatusConsulta;
import br.com.fiap.agendamento.historico.infrastructure.graphql.dto.*;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ConsultaGraphQLMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_TIME;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public ConsultaGraphQLDTO toDTO(ConsultaHistorico consulta) {
        return ConsultaGraphQLDTO.builder()
                .id(consulta.getId().toString())
                .paciente(toPacienteDTO(consulta.getPaciente()))
                .medico(toMedicoDTO(consulta.getMedico()))
                .hospital(toHospitalDTO(consulta.getHospital()))
                .data(consulta.getData().format(DATE_FORMATTER))
                .horario(consulta.getHorario().format(TIME_FORMATTER))
                .status(consulta.getStatus().name())
                .criadoEm(consulta.getCriadoEm().format(DATE_TIME_FORMATTER))
                .build();
    }

    private PacienteGraphQLDTO toPacienteDTO(br.com.fiap.agendamento.historico.domain.usuario.entity.PacienteHistorico paciente) {
        return PacienteGraphQLDTO.builder()
                .id(paciente.getId().toString())
                .nome(paciente.getNome())
                .email(paciente.getEmail())
                .telefone(paciente.getTelefone())
                .build();
    }

    private MedicoGraphQLDTO toMedicoDTO(br.com.fiap.agendamento.historico.domain.usuario.entity.MedicoHistorico medico) {
        return MedicoGraphQLDTO.builder()
                .id(medico.getId().toString())
                .nome(medico.getNome())
                .email(medico.getEmail())
                .build();
    }

    private HospitalGraphQLDTO toHospitalDTO(br.com.fiap.agendamento.historico.domain.hospital.entity.HospitalHistorico hospital) {
        return HospitalGraphQLDTO.builder()
                .id(hospital.getId().toString())
                .nome(hospital.getNome())
                .build();
    }
}
