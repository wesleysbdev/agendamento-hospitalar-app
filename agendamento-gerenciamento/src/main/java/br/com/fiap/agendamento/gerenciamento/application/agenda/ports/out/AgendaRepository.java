package br.com.fiap.agendamento.gerenciamento.application.agenda.ports.out;

import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgendaRepository {
    List<Agenda> listar();

    Optional<Agenda> buscarPorUuid(UUID uuid);

    List<Agenda> buscarPorMedico(UUID medicoUuid);

    List<Agenda> buscarPorHospital(UUID hospitalUuid);

    Agenda salvar(Agenda agenda);
}
