package br.com.fiap.agendamento.gerenciamento.domain.agenda.entity;

import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Medico;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class Agenda {

    private final UUID uuid;
    private Medico medico;
    private Hospital hospital;
    private List<LocalTime> horarios;

    public Agenda(UUID uuid, Medico medico, Hospital hospital, List<LocalTime> horarios) {
        this.uuid = uuid;
        this.medico = medico;
        this.hospital = hospital;
        this.horarios = horarios;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Medico getMedico() {
        return medico;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public List<LocalTime> getHorarios() {
        return horarios;
    }
}
