package br.com.fiap.agendamento.gerenciamento.domain.consulta.entity;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.enums.ConsultaEstado;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Medico;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Paciente;

import java.time.LocalTime;
import java.util.UUID;

public class Consulta {

    private final UUID uuid;
    private LocalTime horario;
    private Medico medico;
    private Paciente paciente;
    private Hospital hospital;
    private ConsultaEstado estado;

    public Consulta(UUID uuid, LocalTime horario, Medico medico, Paciente paciente, Hospital hospital, ConsultaEstado estado) {
        this.uuid = uuid;
        this.horario = horario;
        this.medico = medico;
        this.paciente = paciente;
        this.hospital = hospital;
        this.estado = estado;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public ConsultaEstado getEstado() {
        return estado;
    }
}
