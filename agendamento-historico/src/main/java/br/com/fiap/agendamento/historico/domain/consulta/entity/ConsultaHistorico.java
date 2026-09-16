package br.com.fiap.agendamento.historico.domain.consulta.entity;

import br.com.fiap.agendamento.historico.domain.consulta.enums.StatusConsulta;
import br.com.fiap.agendamento.historico.domain.hospital.entity.HospitalHistorico;
import br.com.fiap.agendamento.historico.domain.usuario.entity.MedicoHistorico;
import br.com.fiap.agendamento.historico.domain.usuario.entity.PacienteHistorico;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class ConsultaHistorico {

    private final UUID id;
    private final PacienteHistorico paciente;
    private final MedicoHistorico medico;
    private final HospitalHistorico hospital;
    private final LocalDate data;
    private final LocalTime horario;
    private final StatusConsulta status;
    private final LocalDateTime criadoEm;

    public ConsultaHistorico(UUID id, PacienteHistorico paciente, MedicoHistorico medico, 
                            HospitalHistorico hospital, LocalDate data, LocalTime horario, 
                            StatusConsulta status, LocalDateTime criadoEm) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.hospital = hospital;
        this.data = data;
        this.horario = horario;
        this.status = status;
        this.criadoEm = criadoEm;
    }

    public UUID getId() {
        return id;
    }

    public PacienteHistorico getPaciente() {
        return paciente;
    }

    public MedicoHistorico getMedico() {
        return medico;
    }

    public HospitalHistorico getHospital() {
        return hospital;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
