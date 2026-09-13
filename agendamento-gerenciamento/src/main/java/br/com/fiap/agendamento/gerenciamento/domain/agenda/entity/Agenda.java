package br.com.fiap.agendamento.gerenciamento.domain.agenda.entity;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.fiap.agendamento.gerenciamento.domain.agenda.exception.AgendaDadosInvalidosException;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Medico;

public class Agenda {

	private final UUID uuid;
	private final Medico medico;
	private final Hospital hospital;
	private final List<HorarioAgenda> horarios;

	public Agenda(UUID uuid, Medico medico, Hospital hospital, List<HorarioAgenda> horarios) {
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

		this.uuid = uuid;
		this.medico = medico;
		this.hospital = hospital;
		this.horarios = new ArrayList<>(horarios);

		validarHorarios();
	}

	private void validarHorarios() {
		Set<HorarioAgenda> horariosUnicos = new HashSet<>(horarios);

		if (horariosUnicos.size() != horarios.size()) {
			throw new AgendaDadosInvalidosException("A agenda não pode possuir horários duplicados");
		}
		
		for (Iterator iterator = horariosUnicos.iterator(); iterator.hasNext();) {
			HorarioAgenda horarioAgenda = (HorarioAgenda) iterator.next();
			horarioAgenda.getHorario();
		}

		horariosUnicos.forEach(horario -> {
			validarDiaSemana(horario);
			validarHorarioAtendimento(horario);
		});
	}
	
	private void validarIntervaloMinimoConsultas() {
	    if (hospital.getTempoMinimoConsulta() == null || horarios.isEmpty()) {
	        return;
	    }

	    Duration tempoMinimo = hospital.getTempoMinimoConsulta();

	    // Agrupa os horários por dia da semana
	    var horariosPorDia = horarios.stream()
	            .collect(Collectors.groupingBy(HorarioAgenda::getDayOfWeek));

	    for (var entry : horariosPorDia.entrySet()) {
	        List horariosDoDia = entry.getValue();
	        
	        // Ordena cronologicamente no dia
	        horariosDoDia.sort(Comparator.comparing(HorarioAgenda::getHorario));

	        for (int i = 0; i < horariosDoDia.size() - 1; i++) {
	            LocalTime atual = horariosDoDia.get(i).getHorario();
	            LocalTime proximo = horariosDoDia.get(i + 1).getHorario();

	            // Verifica se a próxima consulta começa antes do tempo mínimo exigido terminar
	            if (proximo.isBefore(atual.plus(tempoMinimo))) {
	                throw new IllegalStateException(
	                    String.format("O intervalo entre %s e %s viola o tempo mínimo de consulta de %d minutos exigido pelo hospital.",
	                        atual, proximo, tempoMinimo.toMinutes())
	                );
	            }
	        }
	    }
	}

	private void validarHorarioAtendimento(HorarioAgenda horario) {
		if (!horario.getHorario().isAfter(hospital.getHoraInicio())
				|| !horario.getHorario().isBefore(hospital.getHoraFim())) {
			throw new AgendaDadosInvalidosException(
					"Os horários de atendimento devem respeitar as regras da unidade hospitalar");
		}
	}

	private void validarDiaSemana(HorarioAgenda horario) {
		if (horario.getDiaSemana().compareTo(hospital.getDiaSemanaInicio()) < 0
				|| horario.getDiaSemana().compareTo(hospital.getDiaSemanaFim()) > 0) {
			throw new AgendaDadosInvalidosException(
					"Os dias de atendimento devem respeitar as regras da unidade hospitalar");
		}
	}

	public void adicionarHorario(HorarioAgenda horario) {
		if (horario == null) {
			throw new AgendaDadosInvalidosException("Horário é obrigatório");
		}

		if (horarios.contains(horario)) {
			throw new AgendaDadosInvalidosException("Horário já existe na agenda");
		}

		horarios.add(horario);
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
