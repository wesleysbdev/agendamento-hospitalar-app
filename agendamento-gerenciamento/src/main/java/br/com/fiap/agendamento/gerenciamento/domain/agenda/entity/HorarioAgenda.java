package br.com.fiap.agendamento.gerenciamento.domain.agenda.entity;

import br.com.fiap.agendamento.gerenciamento.domain.agenda.exception.AgendaDadosInvalidosException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public class HorarioAgenda {

    private final UUID id;
    private final DayOfWeek diaSemana;
    private final LocalTime horario;

    public HorarioAgenda(UUID id, DayOfWeek diaSemana, LocalTime horario) {
        validarDadosObrigatorios(id, diaSemana, horario);
        this.id = id;
        this.diaSemana = diaSemana;
        this.horario = horario;
    }

    private void validarDadosObrigatorios(UUID id, DayOfWeek diaSemana, LocalTime horario) {
        if (id == null) {
            throw new AgendaDadosInvalidosException("UUID é obrigatório");
        }

        if (diaSemana == null) {
            throw new AgendaDadosInvalidosException("Dia da semana é obrigatório");
        }

        if (horario == null) {
            throw new AgendaDadosInvalidosException("Horário é obrigatório");
        }
    }

    public UUID getId() {
        return id;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHorario() {
        return horario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HorarioAgenda outro)) return false;

        return diaSemana == outro.diaSemana
                && horario.equals(outro.horario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaSemana, horario);
    }
}
