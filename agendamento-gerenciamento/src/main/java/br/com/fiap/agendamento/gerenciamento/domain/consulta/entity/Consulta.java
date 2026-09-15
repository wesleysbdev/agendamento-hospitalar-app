package br.com.fiap.agendamento.gerenciamento.domain.consulta.entity;

import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.enums.StatusConsulta;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.exception.ConsultaDadosInvalidosException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Paciente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Consulta {

    private final UUID id;
    private final Paciente paciente;
    private final Agenda agenda;
    private final LocalDate data;
    private final LocalTime horario;
    private StatusConsulta status;

    private Consulta(UUID id, Paciente paciente, Agenda agenda, LocalDate data, LocalTime horario) {
        this.id = id;
        this.paciente = paciente;
        this.agenda = agenda;
        this.data = data;
        this.horario = horario;
        this.status = StatusConsulta.AGENDADA;
    }

    public static Consulta agendar(UUID uuid, Paciente paciente, Agenda agenda, LocalDate data, LocalTime horario) {
        if (uuid == null) {
            throw new ConsultaDadosInvalidosException("UUID é obrigatório");
        }

        if (paciente == null) {
            throw new ConsultaDadosInvalidosException("Paciente é obrigatório");
        }

        if (agenda == null) {
            throw new ConsultaDadosInvalidosException("Agenda é obrigatória");
        }

        if (data == null) {
            throw new ConsultaDadosInvalidosException("Data é obrigatória");
        }

        if (horario == null) {
            throw new ConsultaDadosInvalidosException("Horário é obrigatório");
        }

        if (!agenda.possuiHorario(data.getDayOfWeek(), horario)) {
            throw new ConsultaDadosInvalidosException("Horário não pertence à agenda");
        }

        return new Consulta(uuid, paciente, agenda, data, horario);
    }

    public void cancelar() {
        if (status != StatusConsulta.AGENDADA && status != StatusConsulta.CONFIRMADA) {
            throw new ConsultaDadosInvalidosException("A consulta não pode ser cancelada");
        }

        status = StatusConsulta.CANCELADA;
    }

    public void realizar() {
        if (status != StatusConsulta.AGENDADA && status != StatusConsulta.CONFIRMADA) {
            throw new ConsultaDadosInvalidosException("A consulta não pode ser realizada");
        }

        status = StatusConsulta.REALIZADA;
    }

    public void confirmar() {
        if (status != StatusConsulta.AGENDADA) {
            throw new ConsultaDadosInvalidosException("Somente consultas agendadas podem ser confirmadas");
        }

        status = StatusConsulta.CONFIRMADA;
    }

    public void marcarComoAusente() {
        if (status != StatusConsulta.CONFIRMADA) {
            throw new ConsultaDadosInvalidosException("Consulta não pode ser marcada como ausente");
        }

        status = StatusConsulta.AUSENTE;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public UUID getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHorario() {
        return horario;
    }

}