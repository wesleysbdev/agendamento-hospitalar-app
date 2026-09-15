package br.com.fiap.agendamento.gerenciamento.domain.agenda.entity;

import br.com.fiap.agendamento.gerenciamento.domain.agenda.exception.AgendaDadosInvalidosException;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Medico;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class Agenda {

    private final UUID uuid;
    private final Medico medico;
    private final Hospital hospital;
    private List<HorarioAgenda> horarios;

    public Agenda(UUID uuid, Medico medico, Hospital hospital, List<HorarioAgenda> horarios) {
        validarDadosObrigatorios(uuid, medico, hospital, horarios);

        this.uuid = uuid;
        this.medico = medico;
        this.hospital = hospital;
        this.horarios = new ArrayList<>(horarios);

        validarHorarios(this.horarios);
    }

    private void validarDadosObrigatorios(UUID uuid, Medico medico, Hospital hospital, List<HorarioAgenda> horarios) {
        if (uuid == null) {
            throw new AgendaDadosInvalidosException("UUID da agenda é obrigatório");
        }

        if (medico == null) {
            throw new AgendaDadosInvalidosException("Médico é obrigatório");
        }

        if (hospital == null) {
            throw new AgendaDadosInvalidosException("Hospital é obrigatório");
        }

        if (horarios == null || horarios.isEmpty()) {
            throw new AgendaDadosInvalidosException("A agenda deve possuir pelo menos um horário");
        }
    }

    private void validarHorarios(List<HorarioAgenda> horarios) {
        List<HorarioAgenda> horariosOrdenados = horarios.stream()
                .distinct()
                .sorted(Comparator.comparing(HorarioAgenda::getDiaSemana)
                        .thenComparing(HorarioAgenda::getHorario))
                .toList();

        if (horariosOrdenados.size() != horarios.size()) {
            throw new AgendaDadosInvalidosException("A agenda não pode possuir horários duplicados");
        }

        HorarioAgenda anterior = null;

        for (HorarioAgenda atual : horariosOrdenados) {

            validarDiaSemana(atual.getDiaSemana());
            validarHorarioAtendimento(atual.getHorario());

            if (anterior != null) {
                if (atual.getDiaSemana().equals(anterior.getDiaSemana())) {
                    validarIntervaloEntreHorarios(anterior, atual);
                }
            }

            anterior = atual;
        }
    }

    private void validarHorarioAtendimento(LocalTime horario) {
        if (!hospital.funcionaNoHorario(horario)) {
            throw new AgendaDadosInvalidosException("Os horários de atendimento devem respeitar as regras da unidade hospitalar");
        }
    }

    private void validarDiaSemana(DayOfWeek diaDaSemana) {
        if (!hospital.funcionaNoDia(diaDaSemana)) {
            throw new AgendaDadosInvalidosException("Os dias de atendimento devem respeitar as regras da unidade hospitalar");
        }
    }

    private void validarIntervaloEntreHorarios(HorarioAgenda atual, HorarioAgenda proximo) {

        LocalTime horarioMinimoProximo =
                atual.getHorario().plus(hospital.getTempoMinimoConsulta());

        if (proximo.getHorario().isBefore(horarioMinimoProximo)) {
            throw new AgendaDadosInvalidosException(
                    String.format(
                            "O intervalo entre %s e %s viola o tempo mínimo de consulta de %d minutos exigido pelo hospital.",
                            atual.getHorario(),
                            proximo.getHorario(),
                            hospital.getTempoMinimoConsulta().toMinutes()
                    )
            );
        }
    }

    public void adicionarHorario(HorarioAgenda horario) {
        if (horario == null) {
            throw new AgendaDadosInvalidosException("Horário é obrigatório");
        }

        if (horarios.contains(horario)) {
            throw new AgendaDadosInvalidosException("Horário já existe na agenda");
        }

        List<HorarioAgenda> novosHorarios = new ArrayList<>(horarios);
        novosHorarios.add(horario);

        validarHorarios(novosHorarios);

        horarios.add(horario);
    }

    public void adicionarHorarios(List<HorarioAgenda> horarios) {
        List<HorarioAgenda> novosHorarios = new ArrayList<>(this.horarios);
        novosHorarios.addAll(horarios);

        validarHorarios(novosHorarios);

        this.horarios = novosHorarios;
    }

    public void removerHorario(HorarioAgenda horario) {
        if (!horarios.remove(horario)) {
            throw new AgendaDadosInvalidosException("Horário não existe na agenda");
        }
    }

    public boolean possuiHorario(DayOfWeek diaSemana, LocalTime horario) {
        return horarios.stream().anyMatch(h -> h.getDiaSemana().equals(diaSemana) && h.getHorario().equals(horario));
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

    public List<HorarioAgenda> getHorarios() {
        return List.copyOf(horarios);
    }

}
