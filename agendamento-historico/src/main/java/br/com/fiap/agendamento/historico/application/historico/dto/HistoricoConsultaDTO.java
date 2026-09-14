package br.com.fiap.agendamento.historico.application.historico.dto;

import br.com.fiap.agendamento.historico.domain.historico.enums.EstadoConsulta;

import java.time.LocalDateTime;
import java.util.UUID;

public class HistoricoConsultaDTO {
    private UUID consultaUuid;
    private LocalDateTime horario;
    private EstadoConsulta estado;
    private PacienteHistoricoDTO paciente;
    private MedicoHistoricoDTO medico;
    private HospitalHistoricoDTO hospital;

    public HistoricoConsultaDTO() {}

    public HistoricoConsultaDTO(UUID consultaUuid, LocalDateTime horario, EstadoConsulta estado,
                                 PacienteHistoricoDTO paciente, MedicoHistoricoDTO medico,
                                 HospitalHistoricoDTO hospital) {
        this.consultaUuid = consultaUuid;
        this.horario = horario;
        this.estado = estado;
        this.paciente = paciente;
        this.medico = medico;
        this.hospital = hospital;
    }

    public UUID getConsultaUuid() {
        return consultaUuid;
    }

    public void setConsultaUuid(UUID consultaUuid) {
        this.consultaUuid = consultaUuid;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public EstadoConsulta getEstado() {
        return estado;
    }

    public void setEstado(EstadoConsulta estado) {
        this.estado = estado;
    }

    public PacienteHistoricoDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteHistoricoDTO paciente) {
        this.paciente = paciente;
    }

    public MedicoHistoricoDTO getMedico() {
        return medico;
    }

    public void setMedico(MedicoHistoricoDTO medico) {
        this.medico = medico;
    }

    public HospitalHistoricoDTO getHospital() {
        return hospital;
    }

    public void setHospital(HospitalHistoricoDTO hospital) {
        this.hospital = hospital;
    }
}
